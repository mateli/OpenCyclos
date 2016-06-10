Behaviour.register({
	'#changePasswordForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});

Event.observe(self, "load", function() {
	var field = getObject('oldPassword') || getObject('newPassword');
	field.focus();
});