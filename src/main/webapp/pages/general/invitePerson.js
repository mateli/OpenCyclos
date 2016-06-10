Behaviour.register({
	'#inviteForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});