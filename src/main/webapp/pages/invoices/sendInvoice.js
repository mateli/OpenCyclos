var transferTypesByCurrency = null
var accountTypesByCurrency = null

function updateTransferTypes(tts) {
	if (isEmpty(getValue("to"))) {
		return;
	}
	tts = tts || types;
	types = tts;
	var cell = $('typeCell');
	var showRow = false;
	cell.innerHTML = "";
	var disableInvoice = false;
	
	//Get the transfer types by currency
	transferTypesByCurrency = new Map();
	tts.each(function(tt) {
		var currentTTs = transferTypesByCurrency.get(tt.currencyId)
		if (currentTTs == null) {
			currentTTs = [];
			transferTypesByCurrency.put(tt.currencyId, currentTTs);
		}
		currentTTs.push(tt);
	});

	//Disable those currencies with no transfer types
	var currencyId = getValue("currency");
	var currencySelect = $('currencySelect')
	if (currencySelect) {
		var selectedIndex = currencySelect.selectedIndex;
		for (var i = 0; i < currencySelect.options.length; i++) {
			var option = currencySelect.options[i];
			var currentCurrency = option.value;
			var currentTTs = transferTypesByCurrency.get(currentCurrency);
			option.disabled = isEmpty(currentTTs);
		}
		//When the selected currency was disabled, try to select another one
		if (currencySelect.options[selectedIndex].disabled) {
			var couldSelectAnotherOne = false;
			for (var i = 0; i < currencySelect.options.length; i++) {
				var option = currencySelect.options[i];
				if (!option.disabled) {
					currencySelect.selectedIndex = i;
					currencyId = option.value;
					couldSelectAnotherOne = true;
					break;
				}
			}
			if (!couldSelectAnotherOne) {
				disableInvoice = true;
			}
		}
	}
	
	//Get the transfer types for the selected currency
	if (!disableInvoice) {
		tts = transferTypesByCurrency.get(currencyId);
		if (isEmpty(tts)) {
			disableInvoice = true;
		}
	}
	
	if (!disableInvoice && tts != null) {
		if (tts.length == 1) {
			var tt = tts[0];
	
			var hidden = document.createElement("input");
			hidden.setAttribute("type", "hidden");
			hidden.setAttribute("name", "type");
			hidden.setAttribute("id", "type");
			hidden.setAttribute("value", tt.id);
			cell.appendChild(hidden);
			
			var text = document.createElement("input");
			text.setAttribute("class", "InputBoxDisabled");
			text.setAttribute("readonly", "readonly");
			text.setAttribute("size", "40");
			text.setAttribute("value", tt.name);
			cell.appendChild(text);
		} else {
			var select = document.createElement("select");
			select.setAttribute("name", "type");
			select.setAttribute("id", "type");
			cell.appendChild(select);
			select.onchange = function() {
				updateSchedulingFields();
				updateCustomFields(getValue(this), null);
			}

			
			//types.each(function(tt) {
			tts.each(function(tt) {
				select.options[select.options.length] = new Option(tt.name, tt.id);
			});
			showRow = true;
		}
	}
	if (disableInvoice) {
		disableField("amount");
		disableField("description");
		disableField("submitButton");
		alert(noTransferTypeMessage);
		setFocus(lastMemberFieldWithFocus)
	} else {
		enableField("amount");
		enableField("description");
		enableField("submitButton");
	}
	
	(showRow ? Element.show : Element.hide)("typeRow");
	updateSchedulingFields(false);
	if (tts && tts.length > 0) {
		updateCustomFields(tts[0].id, null);
	}
}

function updateDestAccountTypes(dats) {
	dats = dats || destTypes;
	destTypes = dats;
	var cell = $('destTypeCell');
	var showRow = false;
	cell.innerHTML = "";
	
	var disableInvoice = false;
	
	//Get the account types by currency
	accountTypesByCurrency = new Map();
	dats.each(function(at) {
		var currentATs = accountTypesByCurrency.get(at.currencyId)
		if (currentATs == null) {
			currentATs = [];
			accountTypesByCurrency.put(at.currencyId, currentATs);
		}
		currentATs.push(at);
	});

	//Disable those currencies with no account types
	var currencyId = getValue("currency");
	var currencySelect = $('currencySelect')
	if (currencySelect) {
		var selectedIndex = currencySelect.selectedIndex;
		for (var i = 0; i < currencySelect.options.length; i++) {
			var option = currencySelect.options[i];
			var currentCurrency = option.value;
			var currentATs = accountTypesByCurrency.get(currentCurrency);
			option.disabled = isEmpty(currentATs);
		}
		
		//When the selected currency was disabled, try to select another one
		if (currencySelect.options[selectedIndex].disabled) {
			var couldSelectAnotherOne = false;
			for (var i = 0; i < currencySelect.options.length; i++) {
				var option = currencySelect.options[i];
				if (!option.disabled) {
					currencySelect.selectedIndex = i;
					currencyId = option.value;
					couldSelectAnotherOne = true;
					break;
				}
			}
			if (!couldSelectAnotherOne) {
				disableInvoice = true;
			}
		}
	}
	
	//Get the account types for the selected currency
	if (!disableInvoice) {
		dats = accountTypesByCurrency.get(currencyId);
		if (isEmpty(dats)) {
			disableInvoice = true;
		}
	}
	
	if (!disableInvoice && dats != null) {
		if (dats.length == 1) {
			var at = dats[0];
	
			var hidden = document.createElement("input");
			hidden.setAttribute("type", "hidden");
			hidden.setAttribute("id", "destType");
			hidden.setAttribute("name", "destType");
			hidden.setAttribute("value", at.id);
			cell.appendChild(hidden);
			
			var text = document.createElement("input");
			text.setAttribute("class", "InputBoxDisabled");
			text.setAttribute("readonly", "readonly");
			text.setAttribute("size", "40");
			text.setAttribute("value", at.name);
			cell.appendChild(text);
		} else {
			var select = document.createElement("select");
			select.setAttribute("id", "destType");
			select.setAttribute("name", "destType");
			cell.appendChild(select);
			select.onchange = function() {
				updateSchedulingFields();
				updateCustomFields(null, getValue(this));
			}
			
			dats.each(function(at) {
				select.options[select.options.length] = new Option(at.name, at.id);
			});
			showRow = true;
		}
	}
	if (disableInvoice) {
		disableField("amount");
		disableField("description");
		disableField("submitButton");
		alert(noDestAccountTypeMessage);
		setFocus(lastMemberFieldWithFocus)
	} else {
		enableField("amount");
		enableField("description");
		enableField("submitButton");
	}
	(showRow ? Element.show : Element.hide)("destTypeRow");
	updateSchedulingFields(false);
	if (dats && dats.length > 0) {
		updateCustomFields(null, dats[0].id);
	}
}

function updateCustomFields(ttId, destATId) {
	new Ajax.Request(context + "/do/paymentCustomFields", {
	    method: 'post',
	    //The from and to are swapped because the payment is inverse to the invoice
		parameters: "typeId=" + trim(ttId) + "&destinationAccountTypeId=" + trim(destATId) + "&fromId=" + trim(getValue("to")) + "&toId=" + trim(getValue("from")),
		onSuccess: updatePaymentFieldsCallback
	})
}


function calculateMultiplePayments() {
	var params = $H();
	params.set('amount', getValue('amount'));
	params.set('date', getValue('dateText'));
	params.set('firstPaymentDate', getValue('firstPaymentDateText'));
	params.set('paymentCount', getValue('paymentCountText'));
	params.set('recurrence.number', getValue('recurrenceNumberText'));
	params.set('recurrence.field', getValue('recurrenceFieldSelect'));
	requestValidation(params, pathPrefix + "/calculatePayments", generatePayments);
}

function generatePayments(params) {

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

	var strPayments = '<table class="nested">';
	strPayments += '<tr><th class="tdHeaderContents" width="20%">' + paymentNumberLabel + '</th>';
	strPayments += '<th class="tdHeaderContents" width="40%">' + paymentDateLabel + '</th>';
	strPayments += '<th class="tdHeaderContents" width="40%">' + paymentAmountLabel + '</th></tr>';
	
	payments.each(function(payment, index) {
		strPayments += '<tr><th class="tdHeaderContents">' + (index + 1) + '</th>';
		strPayments += '<td align="center" nowrap>';
		strPayments += '<input type="text" class="newInput dateNoLabel small" name="payments.date" value="' + payment.date + '" size="11">';
		strPayments += '</td>';
		strPayments += '<td align="center" nowrap>';
		strPayments += '<input type="text" class="newInput float small" name="payments.amount" value="' + payment.amount + '" size="12">';
		strPayments += '</td></tr>';
	});
	strPayments += '</table>';
	$('paymentsContainer').innerHTML = strPayments;
	document.getElementsBySelector('input.newInput').each(headBehaviour['input']);
}

function selectionAllowsScheduling() {
	if (!$("trSchedulingType")) {
		return false;
	}
	var form = document.forms[0];
	if (useTransferType && types) {
		var id = getValue("type")
		var tt = types.find(function(tt) {return tt.id == id});
		if (tt == null) {
			return false;
		}
		return booleanValue(tt.allowsScheduledPayments);
	} else if (useDestAccountType && destTypes) {
		var id = getValue("destType");
		var at = destTypes.find(function(at) {return at.id == id});
		if (at == null) {
			return false;
		}
		return booleanValue(at.allowsScheduledPayments);
	}
	return false;
}

function updateSchedulingFields(focusElement) {
	if (!$("schedulingTypeSelect")) {
		return false;
	}
	$$(".scheduling").each(Element.hide);
	var keepSingle = false;
	var keepMulti = false;
	if (selectionAllowsScheduling()) {
		$('trSchedulingType').show();
		var type = getValue("schedulingTypeSelect");
		var elementToFocus = null;
		switch(type) {
			case "SINGLE_FUTURE":
				$$('.singlePayment').each(Element.show);
				elementToFocus = 'scheduleForText';
				keepSingle = true;
				break;
			case "MULTIPLE_FUTURE":
				$$('.multiplePayments').each(Element.show);
				elementToFocus = 'paymentCountText';
				keepMulti = true;
				break;
		}
	}
	var todayPlus1Month = dateParser.format(dateAdd(new Date(), 1, JST_FIELD_MONTH));
	if (!keepSingle) {
		$('scheduleForText').value = todayPlus1Month;
		$('singlePaymentContainer').innerHTML = '';
	}
	if (!keepMulti && $('paymentsContainer')) {
		$('paymentCountText').value = '';
		$('firstPaymentDateText').value = todayPlus1Month;
		$('recurrenceNumberText').value = '1';
		$('recurrenceFieldSelect').selectedIndex = 0;
		$('paymentsContainer').innerHTML = '';
	}
	if (focusElement) {
		setFocus(elementToFocus);
	}
}

function confirmInvoice(params) {
	//If the validation was successful, ask the user the confirmation
	if (params.returnValue) {
		try {
			var message = params.xml.getElementsByTagName("confirmationMessage").item(0).firstChild.data;
			if (!confirm(message)) {
				getObject("amount").focus();
				return false;
			}
		} catch (exception) {
		}
	}
}

function fetchTransferOrAccountTypes() {
	var memberId = getValue("memberId");
	var isToSystem = booleanValue(getValue("toSystem"));
	if ((!isToSystem && isEmpty(memberId))) {
		return;
	}
	var params = $H();
	params.set("scheduling", "true");
	if (useTransferType) {
		params.set("context", "PAYMENT");
		params.set("fromOwnerId", $('memberId').value);
		params.set("toOwnerId", "0");
		params.set("showCurrency", "true");
		params.set("useFromGroup", "true");
		findTransferTypes(params, updateTransferTypes);
	} else {
		params.set("ownerId", isEmpty(fromId) ? "0" : fromId);
		params.set("canPayOwnerId", $('memberId').value);
		findAccountTypes(params, updateDestAccountTypes);
	}
}

Behaviour.register({
	'#description': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			var type = getValue("schedulingTypeSelect");
			switch (type) {
				case "SINGLE_FUTURE":
					$('singlePaymentContainer').innerHTML = "<input name='payments.date' value='" + $('scheduleForText').value + "'><input name='payments.amount' value='" + $('amount').value + "'>";
					return requestValidation(form, null, confirmInvoice);
				case "MULTIPLE_FUTURE":
					if (isEmpty($('paymentsContainer').innerHTML)) {
						$('paymentsContainer').hide();
						if (calculateMultiplePayments()) {
							return requestValidation(form, null, confirmInvoice);
						} 
						return false;
					}
				default:
					return requestValidation(form, null, confirmInvoice);
			}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + profileMemberId;
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, exclude: getValue("from")}, 'memberId', 'memberUsername', 'memberName', 'amount', fetchTransferOrAccountTypes);
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, exclude: getValue("from")}, 'memberId', 'memberUsername', 'memberName', 'amount', fetchTransferOrAccountTypes);
	},
	
	'#schedulingTypeSelect': function(select) {
		select.onchange = updateSchedulingFields.bind(self, true);
	},
	
	'#currencySelect': function(select) {
		select.onchange = function() {
			if (useTransferType) {
				updateTransferTypes();
			} else {
				updateDestAccountTypes();
			}
			setFocus("amount");
		}
	},
	
	'#calculatePaymentsButton': function(button) {
		button.onclick = function() {
			calculateMultiplePayments();
			$('paymentsContainer').show();
		}
	}
});

Event.observe(self, "load", function() {
	var username = $('memberUsername');
	if (username) {
		username.focus();
	} else {
		getObject("amount").focus();
	}
});