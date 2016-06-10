Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#typeSelect': function(select) {
		select.onchange = function() {
			params = $H();
			params.set("type", getValue("typeSelect"));
			params.set("groupFilterId", getValue("file(groupFilter)"));
			new Ajax.Request(pathPrefix + "/getAvailableFilesForGroupFilterAjax", {
				parameters: params.toQueryString(),
				onSuccess: function(request, array) {
					var fileSelect = getObject("fileSelect");
					clearOptions(fileSelect);
					array.each(function(file) {
						fileSelect.options[fileSelect.options.length] = new Option(file, file);
					});
					var selectedFile = getValue(fileSelect);
					if (!isEmpty(selectedFile)) {
						fileSelect.onchange();
					}
				}
			});
		};
	},
	
	'#fileSelect': function(select) {
		select.onchange = function() {
			var fileName = getValue(select);
			if (isEmpty(fileName)) {
				setValue("contents", "");
				return;
			}
			params = $H();
			params.set("type", getValue("typeSelect"));
			params.set("groupFilterId", getValue("file(groupFilter)"));
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
			self.location = pathPrefix + "/editGroupFilter?groupFilterId=" + getValue("file(groupFilter)");
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ["fileType", "fileName"]);
			getObject('contents').focus();
		}
	}
});

Event.observe(self, "load", function() {
	var select = $("typeSelect");
	if (select != null) {
		select.onchange();
	}
});