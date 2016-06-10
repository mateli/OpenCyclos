function isInsert() {
	var id = parseInt(getValue("externalTransferTypeId"));
	return (isNaN(id) || id == 0);
}

function updateFields() {
	var action = getValue('actionSelect');
	var isPaymentToSystem = action == 'GENERATE_SYSTEM_PAYMENT';
	var isPaymentToMember = action == 'GENERATE_MEMBER_PAYMENT';

	if (isPaymentToSystem || isPaymentToMember) {
		setOptions('transferTypeSelect', isPaymentToSystem ? toSystemTTs : toMemberTTs, false, false, 'name', 'id');
		setValue('transferTypeSelect', selectedTT);
		$('trTransferType').show();
	} else {
		$('trTransferType').hide();
	}
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editExternalAccount?externalAccountId="+externalAccountId;
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
			getObject('externalTransferType(name)').focus();
		}
	},
	
	'#actionSelect': function(select) {
		select.onchange = updateFields;
	},
	
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 2000);
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
	updateFields();
});