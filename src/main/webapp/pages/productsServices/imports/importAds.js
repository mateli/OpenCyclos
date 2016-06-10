
Behaviour.register({

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}

});
