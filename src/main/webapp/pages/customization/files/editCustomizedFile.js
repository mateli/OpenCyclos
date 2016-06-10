var directoryContents;

function updateContents() {
	var selectedContentIndex = $('contentSelect').selectedIndex;
	if (booleanValue(directoryContents[selectedContentIndex - 1].directory)) {
		var path = getValue("filePath") + getValue("contentSelect") + "/";
		setValue("filePath", path);
		setValue("contents", "");
		params = $H();
		params.set("path", path);
		findDirectoryContents(params, updateDirectoryContents);
		if (path == '/') {
			showUpLevelLink(false);
		} else {
			showUpLevelLink(true);
		}
	} else {
		var fullPath = getValue("filePath") + getValue("contentSelect")
		updateFileContents(fullPath);
	}
}

function updateFileContents(name) {
	params = $H();
	params.set("type", type);
	params.set("fileName", name);
	new Ajax.Request(pathPrefix + "/getFileContentsAjax", {
		parameters: params.toQueryString(),
		onSuccess: function(request) {
		    enableField("contents");
			setValue("contents", request.responseText);
		}
	});
}

function updateDirectoryContents(newDirectoryContents) {
	directoryContents = newDirectoryContents;
	setOptions('contentSelect', directoryContents, chooseLabel, false, 'name', 'name', chooseLabel);
	setValue("contents", "");
}

function upLevel() {
	var filePath = getValue("filePath");
	var newFilePath = filePath.substring(0, filePath.length-1); // Remove the last '/' character from the end of the path
	var index = newFilePath.lastIndexOf("/")-1;
	newFilePath = newFilePath.substring(0, index+1) + "/";
	if (newFilePath == "/") {
		showUpLevelLink(false);
	} 
	setValue("filePath", newFilePath);
	params = $H();
	params.set("path", newFilePath);
	findDirectoryContents(params, updateDirectoryContents);
}

function showUpLevelLink(show) {
	if (show) {
		Element.show('upLevelLink');
	} else {
		Element.hide('upLevelLink');
	}
}

function showOriginalContents(show) {
	var originalContents = $('originalContents');
	if (!originalContents) {
		return;
	}
	var showOriginalContents = $('showOriginalContents');
	if (show) {
		Element.show('originalContents');
		Element.hide('showOriginalContents');
	} else {
		Element.show('showOriginalContents');
		Element.hide('originalContents');
	}
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			if (type == 'APPLICATION_PAGE') {
				if (isTransient) {
					var fullFileName = getValue("filePath") + getValue("contentSelect")
					setValue('file(name)', fullFileName);
				}
			}
			return requestValidation(form);
		}
	},
	
	'#contentSelect': function(select) {
		select.onchange = updateContents;
	},
	
	'#fileSelect': function(select) {
		select.onchange = function() {
			updateFileContents(getValue("fileSelect"));
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listCustomizedFiles?type=" + type;
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ["fileName", "file(originalContents)", "file(newContents)"]);
			setFocus('contents');
		}
	}
});

Event.observe(self, "load", function() {
	if (type == 'APPLICATION_PAGE') {
		if (isTransient) {
			// Clean fields
			setValue("filePath", "/");
			setValue("contents", "");
			showUpLevelLink(false);
			
			// Get contents of root directory
			params = $H();
			params.set("path", "/");
			findDirectoryContents(params, updateDirectoryContents);
            enableFormForInsert();
            disableField(contents);
		}
	} else {
		var select = $("fileSelect");
		if (select != null) {
			updateFileContents(getValue(select));
		}
		if (isTransient) {
		    enableFormForInsert();
		}
	}
});