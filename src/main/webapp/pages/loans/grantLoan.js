function getCurrentTransferType() {
	var id = getValue("transferType");
	var transferType = null;
	transferTypes.each(function(tt) {
		if (tt.id == id) {
			transferType = tt;
			throw $break;
		}
	});
	return transferType;
}

function updateTransferTypes(tts) {
	transferTypes = tts;
	setOptions("transferType", transferTypes, false, true, "name", "id");
	if (transferTypes.length > 0) {
		setTransferType();
	}
}

function setResponsible() {
	var params = $H();
	params.set('context', 'LOAN');
	params.set('toOwnerId', getValue("responsibleSelect"));
	params.set('loanData', "true");
	findTransferTypes(params, updateTransferTypes);
}

function setTransferType() {
	var transferType = getCurrentTransferType();
	var trsSinglePayment = document.getElementsBySelector('tr.trSinglePayment');
	var trsMultiPayment = document.getElementsBySelector('tr.trMultiPayment');
	var trsWithInterest = document.getElementsBySelector('tr.trWithInterest');
	var all = [].concat(trsWithInterest, trsSinglePayment, trsMultiPayment);
	all.each(function(tr) {
		Element.hide(tr);
	});
	var toShow = [];
	var keepHiddenIds = [];
	var loanType = transferType ? transferType.loanType : '';
	switch (loanType) {
		case 'SINGLE_PAYMENT':
			toShow = trsSinglePayment;
			if (!isEmpty(transferType.repaymentDays)) {
				var days = parseInt(transferType.repaymentDays, 10);
				if (!isNaN(days)) {
					var date = dateAdd(new Date(), days);
					setValue("loan(repaymentDate)", dateParser.format(date));
				}
			}
			break;
		case 'MULTI_PAYMENT':
			toShow = trsMultiPayment;
			break;
		case 'WITH_INTEREST':
			toShow = trsWithInterest;
			setValue('monthlyInterest', transferType.monthlyInterest);
			setValue('grantFee', transferType.grantFee);
			setValue('expirationFee', transferType.expirationFee);
			setValue('expirationDailyInterest', transferType.expirationDailyInterest);
			if (isEmpty(transferType.monthlyInterest)) {
				keepHiddenIds.push('trMonthlyInterest');
			}
			if (isEmpty(transferType.grantFee)) {
				keepHiddenIds.push('trLoanGrantFee');
			}
			if (isEmpty(transferType.expirationFee)) {
				keepHiddenIds.push('trLoanExpirationFee');
			}
			if (isEmpty(transferType.expirationDailyInterest)) {
				keepHiddenIds.push('trLoanExpirationDailyInterest');
			}
			break;
	}
	toShow.each(function(tr) {
		if (isEmpty(tr.id) || !keepHiddenIds.include(tr.id)) {
			Element.show(tr);
		}
	});
	updateCustomFields(transferType.id);
}

function updateCustomFields(ttId) {
	new Ajax.Request(context + "/do/paymentCustomFields", {
	    method: 'post',
	    //The from and to are swapped because the payment is inverse to the invoice
		parameters: "forLoan=true&typeId=" + trim(ttId),
		onSuccess: updatePaymentFieldsCallback
	})
}

function paymentsHTML(params, readonly) {
	var payments = [];
	if (params.returnValue) {
		try {
			payments = eval(params.xml.getElementsByTagName("payments").item(0).firstChild.data);
		} catch (exception) {
		}
	}

	if (payments.length == 0) {
		return "";
	}

	var strPayments = '<table width="70%">';
	strPayments += '<tr><th class="tdHeaderContents" width="20%">' + paymentLabel + '</th>';
	strPayments += '<th class="tdHeaderContents" width="40%">' + expirationDateLabel + '</th>';
	strPayments += '<th class="tdHeaderContents" width="40%">' + amountLabel + '</th></tr>';

	payments.each(function(payment, index) {
		strPayments += '<tr><th class="tdHeaderContents">' + (index + 1) + '</th>';
		strPayments += '<td align="center" nowrap>';
		if (!readonly) {
			strPayments += '<input type="text" class="newInput dateNoLabel small" name="loan(payments).expirationDate" value="' + payment.expirationDate + '" size="11">';
		} else {
			strPayments += payment.expirationDate;
		}
		strPayments += '</td>';
		strPayments += '<td align="center" nowrap>';
		if (!readonly) {
			strPayments += '<input type="text" class="newInput float small" name="loan(payments).amount" value="' + payment.amount + '" size="12">';
		} else {
			strPayments += payment.amount;
		}
		strPayments += '</td></tr>';
	});
	strPayments += '</table>';
	
	return strPayments;
}

function calculatePaymentsWithInterest(params) {
	$('paymentProjection').innerHTML = paymentsHTML(params, true);
}

function calculatePaymentsMultiPayment(params) {
	$('paymentList').innerHTML = paymentsHTML(params, false);
	// Apply the behaviour
	document.getElementsBySelector('input.newInput').each(headBehaviour['input']);
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

Behaviour.register({
	'#description': function(textarea) {
		new SizeLimit(textarea, 4000);
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#setDateCheck': function(check) {
		check.onclick = updatePast;
	},
	
	'#showProjectionButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('transferType', getValue('transferType'));
			params.set('amount', getValue('amount'));
			params.set('date', getValue('dateText'));
			params.set('firstExpirationDate', getValue('loan(firstRepaymentDate)'));
			params.set('paymentCount', getValue('loan(paymentCount)'));
			requestValidation(params, pathPrefix + "/loanCalculatePayments", calculatePaymentsWithInterest);
		}
	},
	
	'#calculatePaymentsButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('transferType', getValue('transferType'));
			params.set('amount', getValue('amount'));
			params.set('date', getValue('dateText'));
			params.set('firstExpirationDate', getValue('firstExpirationDate'));
			params.set('paymentCount', getValue('paymentCount'));
			requestValidation(params, pathPrefix + "/loanCalculatePayments", calculatePaymentsMultiPayment);
		}
	},
	
	'#responsibleSelect': function(select) {
		select.onchange = setResponsible;
	},
	
	'#transferType': function(select) {
		select.onchange = setTransferType;
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var memberId = parseInt(getValue('memberId'), 10);
			var loanGroupId = parseInt(getValue('loanGroupId'), 10);
			if (!isNaN(memberId) && memberId > 0) {
				self.location = pathPrefix + "/profile?memberId=" + memberId;
			} else if (!isNaN(loanGroupId) && loanGroupId > 0) {
				self.location = pathPrefix + "/editLoanGroup?loanGroupId=" + loanGroupId;
			} else {
				history.back();
			}
		}
	}
});

Event.observe(self, "load", function() {
	updatePast();
	var loanGroupId = parseInt(getValue('loanGroupId'), 10);
	if (!isNaN(loanGroupId) && loanGroupId > 0) {
		setResponsible();
	} else {
		updateTransferTypes(transferTypes);
	}
});