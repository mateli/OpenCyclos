function isInsert() {
	var id = parseInt(getValue("currencyId"));
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listCurrencies";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('currency(name)').focus();
		}
	},

	
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	}
	
});


Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});