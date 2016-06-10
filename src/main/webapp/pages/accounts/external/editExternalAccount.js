function isInsert() {
	var id = parseInt(document.forms.editExternalAccountForm.externalAccountId.value);
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listExternalAccounts";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyExternalAccountButton': function(button) {
		button.onclick = function() {
			modifyButtonName = "modifyExternalAccountButton";
			saveButtonName = "saveExternalAccountButton";
			enableFormFields.apply(button.form);
			getObject('externalAccount(name)').focus();
		}
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		modifyButtonName = "modifyExternalAccountButton";
		saveButtonName = "saveExternalAccountButton";
		enableFormForInsert();
	}
});