function confirmChangeGroup(params) {
	//If the validation was successful, ask the user the confirmation
	if (params.returnValue) {
		try {
			var element = params.xml.getElementsByTagName("confirmationMessage");
			if (element.length > 0) {
				var message = element.item(0).firstChild.data;
				if (!confirm(message)) {
					setFocus('comments');
					return false;
				}
			}
		} catch (exception) {
		}
	}
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form, null, confirmChangeGroup);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},

	'#removeButton': function(button) {
		button.onclick = function() {
			if (confirm(permanentRemoveConfirmationMessage)) {
				self.location = pathPrefix + "/removeMember?memberId=" + getValue("memberId");
			}
		}
	},

	'#comments': function(textarea) {
		new SizeLimit(textarea, 4000);
	}	
});

Event.observe(self, "load", function() {
	setFocus('comments');
});