Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/operatorProfile?operatorId=" + getValue("operatorId");
		}
	},

	'#removeButton': function(button) {
		button.onclick = function() {
			if (confirm(permanentRemoveConfirmationMessage)) {
				self.location = pathPrefix + "/removeOperator?operatorId=" + getValue("operatorId");
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