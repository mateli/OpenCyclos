function afterSelectingMember(member) {
	setValue("memberId", member == null ? "" : member.id);
	fetchTransferTypes();
}

function fetchTransferTypes() {
	var memberId = getValue("memberId");
	var isToSystem = booleanValue(getValue("toSystem"));
	if (!isToSystem && isEmpty(memberId)) {
		return;
	}
	var params = $H();
	var from = getValue("from");
	params.set("channel", "web");
	params.set("context", "PAYMENT");
	params.set("showCurrency", "true");
	params.set("fromOwnerId", isEmpty(from) ? "0" : from);
	if (isToSystem) {
		params.set("toNature", "SYSTEM");
	} else {
		params.set("toOwnerId", memberId);
	}
	params.set("useBy", "true");
	params.set("scheduling", "true");
	findTransferTypes(params, updateTransferTypes);
}

function afterUpdatingTransferTypes(tts) {
	updateSchedulingFields(false);
}

function selectedTransferTypeAllowsScheduling() {
	if (!$("schedulingTypeSelect")) {
		return false;
	}
	var id = getValue("type");
	var form = document.forms[0];
	var tt = transferTypes.find(function(tt) {return tt.id == id});
	if (tt == null) {
		return false;
	}
	return booleanValue(tt.allowsScheduledPayments);
}

function updateSchedulingFields(focusElement) {
	if (!$("schedulingTypeSelect")) {
		return false;
	}
	$$(".scheduling").each(Element.hide);
	var keepSingle = false;
	var keepMulti = false;
	
	var pastDateChecked = false;
	var check = $('setDateCheck');
	if (check && check.checked) {
		pastDateChecked = true;
	}
	
	if (!pastDateChecked && selectedTransferTypeAllowsScheduling()) {
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
	var todayPlus1Month = dateParser.format(dateAdd(today, 1, JST_FIELD_MONTH));
	if (!keepSingle) {
		$('scheduleForText').value = todayPlus1Month;
		$('singlePaymentContainer').innerHTML = '';
	}
	if (!keepMulti && $('paymentsContainer')) {
		$('paymentCountText').value = '';
		$('firstPaymentDateText').value = dateParser.format(today);
		$('recurrenceNumberText').value = '1';
		$('recurrenceFieldSelect').selectedIndex = 0;
		$('paymentsContainer').innerHTML = '';
	}
	if (focusElement) {
		setFocus(elementToFocus);
	}
}
transferTypeChangeFunction = updateSchedulingFields;

function calculateMultiplePayments(callback) {
	var params = $H();
	params.set('from', getValue('from'));
	params.set('amount', getValue('amount'));
	params.set('date', getValue('dateText'));
	params.set('firstPaymentDate', getValue('firstPaymentDateText'));
	params.set('paymentCount', getValue('paymentCountText'));
	params.set('recurrence.number', getValue('recurrenceNumberText'));
	params.set('recurrence.field', getValue('recurrenceFieldSelect'));
	requestValidation(params, pathPrefix + "/calculatePayments", function(params) {
		generatePayments(params);
		if (callback && params.returnValue) {
			callback();
		}
	});
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
		$('paymentsContainer').innerHTML = "";
		return;
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

function prepareForm(form, callback) {
	var type = getValue("schedulingTypeSelect");
	switch (type) {
		case "SINGLE_FUTURE":
			$('singlePaymentContainer').innerHTML = "<input name='payments.date' value='" + $('scheduleForText').value + "'><input name='payments.amount' value='" + $('amount').value + "'>";
			break;
		case "MULTIPLE_FUTURE":
			if (isEmpty($('paymentsContainer').innerHTML)) {
				$('paymentsContainer').hide();
				calculateMultiplePayments(callback);
				return;
			}
			break;
	}
	callback();
}

var lastMemberFieldWithFocus = null;
Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else if (!isEmpty(profileMemberId)) {
				self.location = pathPrefix + "/profile?memberId=" + profileMemberId;
			} else {
			    history.back();
			}
		}
	},
	
	'#schedulingTypeSelect': function(select) {
		select.onchange = updateSchedulingFields.bind(self, true);
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, exclude: getValue("from")}, 'memberId', 'memberUsername', 'memberName', 'amount', afterSelectingMember);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, exclude: getValue("from")}, 'memberId', 'memberUsername', 'memberName', 'amount', afterSelectingMember);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#currencySelect': function(select) {
		select.onchange = function() {
			updateTransferTypes();
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

	if (!booleanValue(getValue("selectMember"))) {	
		updateTransferTypes();
	}
	setValue("amount", '');
});