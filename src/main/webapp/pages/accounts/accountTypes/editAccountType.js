function isInsert() {
	var id = parseInt(getValue("accountTypeId"));
	return (isNaN(id) || id == 0);
}

function isLimited() {
	return getValue("accountType(limitType)") == "LIMITED";
}

function updateLimits() {
	var value = getValue("natureSelect");
	var isSystemSelected = value == "SYSTEM";
	if (isSystem || isSystemSelected) {
		Element.show('trLimitType');
		try {
			var showOrHide = Element[isLimited() ? 'show' : 'hide'];
			showOrHide('trCreditLimit');
			showOrHide('trUpperCreditLimit');
		} catch (e) {}
	} else {
		try {
			Element.hide('trLimitType');
		} catch (e) {}
		try {
			Element.hide('trCreditLimit');
		} catch (e) {}
		try {
			Element.hide('trUpperCreditLimit');
		} catch (e) {}
	}
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listAccountTypes";
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['limitTypeText', 'currencyText']);
			getObject('accountType(name)').focus();
		}
	},
	
	'#natureSelect': function(select) {
		select.onchange = updateLimits;
	},
	
	'#limitTypeSelect': function(select) {
		select.onchange = updateLimits;
	},
	
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'#newTransferTypeButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editTransferType?accountTypeId=" + getValue("accountTypeId");
		}
	},
	
	'img.transferTypeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editTransferType?accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + img.getAttribute("transferTypeId");
		}
	},
	
	'img.removeTransferType': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeTransferTypeConfirmation)) {
				self.location = pathPrefix + "/removeTransferType?accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + img.getAttribute("transferTypeId");
			}
		}
	},
	
	'#newAccountFeeButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editAccountFee?accountTypeId=" + getValue("accountTypeId");
		}
	},
	
	'img.accountFeeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editAccountFee?accountTypeId=" + getValue("accountTypeId") + "&accountFeeId=" + img.getAttribute("accountFeeId");
		}
	},
	
	'img.removeAccountFee': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeAccountFeeConfirmation)) {
				self.location = pathPrefix + "/removeAccountFee?accountTypeId=" + getValue("accountTypeId") + "&accountFeeId=" + img.getAttribute("accountFeeId");
			}
		}
	},
	
	'#newPaymentFilterButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editPaymentFilter?accountTypeId=" + getValue("accountTypeId");
		}
	},
	
	'img.paymentFilterDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editPaymentFilter?accountTypeId=" + getValue("accountTypeId") + "&paymentFilterId=" + img.getAttribute("paymentFilterId");
		}
	},
	
	'img.removePaymentFilter': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removePaymentFilterConfirmation)) {
				self.location = pathPrefix + "/removePaymentFilter?accountTypeId=" + getValue("accountTypeId") + "&paymentFilterId=" + img.getAttribute("paymentFilterId");
			}
		}
	}
});

Event.observe(self, "load", function() {
	updateLimits();
	if (isInsert()) {
		enableFormForInsert();
	}
});