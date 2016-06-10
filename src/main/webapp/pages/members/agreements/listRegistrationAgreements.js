Behaviour.register({
	'.details': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editRegistrationAgreement?registrationAgreementId=" + element.getAttribute("registrationAgreementId");
		}
	},
	'.remove': function(element) {
		setPointer(element);
		element.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeRegistrationAgreement?registrationAgreementId=" + element.getAttribute("registrationAgreementId");
			}
		}
	},
	'.view': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editRegistrationAgreement?registrationAgreementId=" + element.getAttribute("registrationAgreementId");
		}
	},
	'#newButton': function(button) {
		element.onclick = function() {
			self.location = pathPrefix + "/editRegistrationAgreement";
		}
	}
});