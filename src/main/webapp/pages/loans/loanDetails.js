function confirmRepayment(params) {
	//If the validation was successful, ask the user the confirmation
	if (params.returnValue) {
		try {
			var message = params.xml.getElementsByTagName("confirmationMessage").item(0).firstChild.data;
			if (!confirm(message)) {
				setFocus("amountText");
				return false;
			}
		} catch (exception) {
		}
	}
}

function updatePast() {
	var check = $('setDateCheck');
	if (!check) return;
	var checked = check.checked;
	Element[checked ? 'show' : 'hide']('trDate');
	if (checked) {
		$('dateText').focus();
	} else {
		$('dateText').value = '';
	}
}

function setExpiredStatus(status) {
	var form = $('expiredStatusForm');
	if (requestValidation(form)) {
		if (confirm(expiredActionConfirmation)) {
			form.status.value = status;
			form.submit();
		}
	}
}

Behaviour.register({
	'#repayForm': function(form) {
		form.onsubmit = function() {
			form.transactionPassword.value = getValue('_transactionPassword');
			return requestValidation(form, null, confirmRepayment);
		}
	},
		
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms.editForm;
			printResults(null, pathPrefix + "/printLoanDetails?loanId=" + form.loanId.value);
		}
	},
	
	'#paymentSelect': function(select) {
		select.onchange = function() {
			var form = $('editForm');
			form.loanPaymentId.value = getValue(select);
			form.method = 'get';
			form.submit();
		}
	},
	
	'#discardForm': function(form) {
		form.onsubmit = function() {
			var repayForm = $('repayForm'); 
			$A(form.elements).each(function (element) {
				var repayFormElement = repayForm.elements[element.name];
				if (repayFormElement) {
					setValue(element, getValue(repayFormElement)); 
				}
			});
			form.transactionPassword.value = getValue('_transactionPassword');
			if (requestValidation(form)) {
				return confirm(discardConfirmationMessage);
			}
			return false;
		}
	},
	
	'#discardButton': function(button) {
		button.onclick = function() {
			var form = $('discardForm');
			if (form.onsubmit() != false) {
				form.submit()
			}
		}
	},
	
	'#setDateCheck': function(check) {
		check.onclick = updatePast;
	},
	
	'.setExpiredStatus': function(button) {
		button.onclick = function() {
			setExpiredStatus(button.getAttribute('status'));
		};
	},

	'#backButton': function(button) {
		button.onclick = function() {
			if (backToSearchLoanPayments) {
				self.location = pathPrefix + "/searchLoanPayments";
			} else {
				var memberId = parseInt(getValue('memberId'), 10);
				var loanGroupId = parseInt(getValue('loanGroupId'), 10);
				var guaranteeId = parseInt(getValue('guaranteeId'), 10);
				if (!isNaN(memberId) && memberId > 0) {
					self.location = pathPrefix + "/searchLoans?memberId=" + memberId;
				} else if (!isNaN(loanGroupId) && loanGroupId > 0) {
					self.location = pathPrefix + "/editLoanGroup?loanGroupId=" + memberId;
				} else if (!isNaN(guaranteeId) && guaranteeId > 0) {
					self.location = pathPrefix + "/guaranteeDetails?guaranteeId=" + guaranteeId;			
				} else {
					self.location = pathPrefix + "/searchLoans";
				}
			}
		}
	},
	
	'#openTransferLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/viewTransaction?transferId=" + a.getAttribute("transferId");
		}
	}
});

function updateCustomFields() {
	if (isEmpty(repaymentTransferTypeId)) {
		updatePaymentFieldsCallback();
		return;
	}
	new Ajax.Request(context + "/do/paymentCustomFields", {
	    method: 'post',
		parameters: "columnWidth=" + encodeURIComponent("35%") + "&typeId=" + repaymentTransferTypeId,
		onSuccess: updatePaymentFieldsCallback
	})
}

Event.observe(self, "load", function() {
	updateCustomFields();
	updatePast();
	setFocus("amountText");
});