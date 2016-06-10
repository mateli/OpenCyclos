Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			try {
				(getObject("general") || getObject("subject") || getObject("body")).focus();
			} catch (e) {}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listMessageSettings";
		}
	}
});