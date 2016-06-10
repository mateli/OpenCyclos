function isInsert() {
	var id = parseInt(getValue("messageId"));
	return isNaN(id) || id == 0;
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchTranslationMessages";
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['messageKey']);
			getObject(isInsert() ? "message(key)" : "message(value)").focus();
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});

Event.observe(self, "load", function() {
	getObject(isInsert() ? "message(key)" : "message(value)").focus();
	if (isInsert()) {
		enableFormForInsert();
	}
});