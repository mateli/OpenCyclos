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

	'#actionSelect': function(select) {
		select.onchange = actionSelected;
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			if (isImport()) {
				if (isEmpty(getValue("upload"))) {
					alert(noFileMessage);
					return false;
				}
				var confirmed = confirm(confirmImportMessage);
				if (confirmed) {
					showMessageDiv();
				}
				return confirmed; 
			}
		}
	}
});