Behaviour.register({
	'#typeSelect': function(select) {
		select.onchange = updateFields;
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listDocuments";
		}
	},
		
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
		
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('document(name)').focus();
		}
	}
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("document(id)"))) {
		enableFormForInsert();
	}
});
