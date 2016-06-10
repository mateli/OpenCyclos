Behaviour.register({
	
	'#backButton': function(button) {
		button.onclick = function() {
		    self.location = pathPrefix + "/listReceiptPrinterSettings";
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, []);
			getObject('receiptPrinterSettings(name)').focus();
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#helpMessageContainer a': function(a) {
	    a.target = "_blank";
	}
	
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("id"))) {
		enableFormForInsert();
	}
})