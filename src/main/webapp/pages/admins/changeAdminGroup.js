Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/adminProfile?adminId=" + getValue("adminId");
		}
	},

	'#removeButton': function(button) {
		button.onclick = function() {
			if (confirm(permanentRemoveConfirmationMessage)) {
				self.location = pathPrefix + "/removeAdmin?adminId=" + getValue("adminId");
			}
		}
	},

	'#comments': function(textarea) {
		new SizeLimit(textarea, 4000);
	}	
});

Event.observe(self, "load", function() {
	$('comments').focus();
});