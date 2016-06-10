Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var userId = document.forms["managePasswordsForm"].userId.value;
			if (ofAdmin) {
				self.location = pathPrefix + "/adminProfile?adminId=" + userId;
			} else if (ofOperator) {
				self.location = pathPrefix + "/operatorProfile?operatorId=" + userId;
			} else {
				self.location = pathPrefix + "/profile?memberId=" + userId;
			}
		}
	},
	
	'#resetAndSendPasswordForm': function(form) {
		form.onsubmit = function() {
			return confirm(resetConfirmation);
		}
	}
});