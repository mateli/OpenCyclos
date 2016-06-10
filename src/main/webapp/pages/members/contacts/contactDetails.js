Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/contacts";
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['usernameText', 'nameText']);
			$('notes').focus();
		}
	},
	
	'#notes': function(textarea) {
		new SizeLimit(textarea, 1000);
	}
});

Event.observe(self, "load", function() {
	if (isEmpty($('notes').value)) {
		enableFormForInsert();
	}
});