function actionSelected() {
	var trFile = $("trFile");
	var actionName; 
	if (isImport()) {
		actionName = pathPrefix + "/importSettings"
		trFile.show();
	} else {
		actionName = pathPrefix + "/exportSettings";
		trFile.hide();
		setValue("upload", null);
	}
	document.forms.manageSettingsForm.action=actionName;
}

function isImport() {
	var actionValue = getValue("actionSelect");
	return actionValue == "IMPORT"
}

Behaviour.register({
	'#importForm': function(form) {
		form.onsubmit = function() {
			if (getValue("fileUpload") == null) {
				alert(noFileMessage);
				return false;
			}
			var confirmed = confirm(confirmImportMessage);
			if (confirmed) {
				showMessageDiv();
			}
			return confirmed;
		}
	},
		
	'#exportButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/exportTranslationMessages";
		}
	},
	
	'#actionSelect': function(select) {
		select.onchange = actionSelected;
	},
	
	'#settingsForm': function(form) {
		form.onsubmit = function() {
			if (isImport()) {
				if (getValue("upload") == null) {
					alert(noSettingsFileMessage);
					return false;
				}
				var confirmed = confirm(confirmImportSettingsMessage);
				if (confirmed) {
					showMessageDiv();
				}
				return confirmed;
			}
		}
	}
	
});