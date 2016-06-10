//Skip the double submit for this page
skipDoubleSubmitCheck = true;

Behaviour.register({
	'#exportForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});