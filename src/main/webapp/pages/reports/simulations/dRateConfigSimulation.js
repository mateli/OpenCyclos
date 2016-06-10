Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/simulations";
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#currencySelect': function(select) {
		select.onchange = function() {
			setValue("reloadData", "true");
			select.form.submit();
		}
	}

});

