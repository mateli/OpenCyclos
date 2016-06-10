function updateFiles() {
	params = $H();
	params.set("type", getValue("file(type)"));
	params.set("groupId", getValue("file(group)"));
	new Ajax.Request(pathPrefix + "/getAvailableFilesForGroupAjax", {
		parameters: params.toQueryString(),
		onSuccess: function(request, array) {
			var fileSelect = getObject("fileSelect");
			if (fileSelect) {
				clearOptions(fileSelect);
				array.each(function(file) {
					fileSelect.options[fileSelect.options.length] = new Option(file, file);
				});
				var selectedFile = getValue(fileSelect);
				if (!isEmpty(selectedFile)) {
					fileSelect.onchange();
				}
			}
		}
	});
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#typeSelect': function(select) {
		select.onchange = updateFiles;
	},
	
	'#fileSelect': function(select) {
		select.onchange = function() {
			var fileName = getValue("fileSelect");
			if (isEmpty(fileName)) {
				setValue("contents", "");
				return;
			}
			params = $H();
			params.set("type", getValue("typeSelect"));
			params.set("groupId", getValue("file(group)"));
			params.set("fileName", fileName);
			new Ajax.Request(pathPrefix + "/getFileContentsAjax", {
				parameters: params.toQueryString(),
				onSuccess: function(request) {
					lastContents = request.responseText;
					setValue("contents", lastContents);
				}
			});
		};
	},

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroup?groupId=" + getValue("file(group)");
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ["fileType", "fileName"]);
			setFocus('contents');
		}
	}
});

Event.observe(self, "load", function() {
    if (isTransient) {
        updateFiles();
    }
});