Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listMessageCategories";
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
			getObject('messageCategory(name)').focus();
		}
	}
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("messageCategory(id)"))) {
		enableFormForInsert();
	}
});