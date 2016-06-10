function updateFields() {
	updateSmsFields();
	updateTransactionNumberFields();
}

function updateTransactionNumberFields() {
	var isTransactionNumberEnabled = $('transactionNumberCheck').checked;
	var call = isTransactionNumberEnabled ? 'show' : 'hide';
	$$('.trTransactionNumber').each(function(tr) {
		Element[call](tr);
	});
}

function updateSmsFields() {
	var isSmsEnabled = $('smsCheck').checked;
	var call = isSmsEnabled ? 'show' : 'hide';
	$$('.trSms').each(function(tr) {
		Element[call](tr);
	});
}


Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			var valid = requestValidation(form);
			if (valid && oldLanguage != getValue("setting(language)")) {
				if (confirm(confirmationMessage)) {
					showMessageDiv();
					return true;
				} 
				return false;
			}
			return valid;
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject("setting(applicationName)").focus();
		}
	},
	
	'#smsCheck': function(check) {
		check.onclick = updateSmsFields;
	},
	
	'#transactionNumberCheck': function(check) {
		check.onclick = updateTransactionNumberFields;
	}
});

Event.observe(self, "load", updateFields)