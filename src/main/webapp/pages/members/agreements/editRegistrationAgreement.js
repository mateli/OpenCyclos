function isInsert() {
	var id = parseInt(getValue("registrationAgreementId"));
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listRegistrationAgreements";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('registrationAgreement(name)').focus();
		}
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});
