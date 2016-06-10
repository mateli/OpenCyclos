Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = document.referrer;
		}
	}
		
});

Event.observe(self, "load", function() {
	setFocus('transactionPassword');
});