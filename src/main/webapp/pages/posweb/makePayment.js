//Global variable used to focus the first custom field, if any
focusFirstPaymentField = true;

function confirmPayment(params) {
	//If the validation was successful, ask the user the confirmation
	if (params.returnValue) {
		try {
			var message = params.xml.getElementsByTagName("confirmationMessage").item(0).firstChild.data;
			if (!confirm(message)) {
				setFocus("amount");
				return false;
			}
		} catch (exception) {
		}
	}
}

function onCurrencyUpdate() {
	var member = getValue("memberId")
	$('passwordRow')["hide"]();
	onMemberUpdate(isEmpty(member) ? null : {id:member})
}

function onMemberUpdate(member) {
	var receipt = $('printLastReceipt');
	if (receipt) {
		receipt.remove();
	}
	var currency = getValue("currency");
	if (isEmpty(member) || isEmpty(currency)) {
		$('transferTypeRow').hide();
		clearOptions('transferType');
	} else {
		var params = $H();
		params.set('context', 'PAYMENT');
		params.set('channel', 'web');
		params.set('fromOwnerId', loggedMember);
		params.set('currencyId', currency);
		params.set('toOwnerId', member.id);
		findTransferTypes(params, updateTransferTypes);
	}
}

function updateTransferTypes(transferTypes) {
	if (!transferTypes || transferTypes.length == 0) {
		alert(noTransferTypeMessage);
		return;
	}
	var showTR = false;
	if (transferTypes.length > 1) {
		showTR = true;
	}
	var emptyOption;
	var method;
	if (showTR) {
		emptyOption = selectTransferTypeMessage;
		method = "show";
	} else {
		emptyOption = false;
		method = "hide";
	}
	setOptions('transferType', transferTypes, emptyOption, false, 'name', 'id');
	$('transferTypeRow')[method]();
	if (showTR) {
		setFocus('transferType');
	}
	if (transferTypes.length == 1) {
		updateCustomFields(transferTypes[0].id);
		updateTransactionPassword(transferTypes[0].id);
	}
}

function updateCustomFields(ttId) {
	if (isEmpty(ttId)) {
		updatePaymentFieldsCallback();
		return;
	}
	new Ajax.Request(context + "/do/paymentCustomFields", {
	    method: 'post',
		parameters: "columnWidth=" + encodeURIComponent("35%") + "&typeId=" + ttId,
		onSuccess: updatePaymentFieldsCallback
	})
}

function initFocus() {
	var amount = getObject("amount");
	if (amount.readonly) {
		setFocus("username");
	} else {
		setFocus(amount);
	}
}

function updateTransactionPassword(ttId) {
	new Ajax.Request(pathPrefix + "/isUsedTransactionPassword", {
	    method: 'post',
		parameters: "transferTypeId=" + ttId,
		onSuccess: updateTransactionPasswordCallback
	})
}

function updateTransactionPasswordCallback(request, result) {
	$('passwordRow')[result.isUsed ? 'show' : 'hide']();		
}

Behaviour.register({
	
	'#amount': function(input) {
		input.onkeypress = function(event) {
			event = event || window.event;
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				setFocus($('currencySelect') ? "currencySelect" : "memberUsername");
			}
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName', 'transactionPassword', onMemberUpdate);
		input.value = '';
		Event.observe(input, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				if ($('memberId').member) {
					setFocus('_transactionPassword');
				}
			}			
		});
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName', 'transactionPassword', onMemberUpdate);
		input.value = '';
		Event.observe(input, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				if ($('memberId').member) {
					setFocus('transactionPassword');
				}
			}			
		});
	},
	
	'#transferType': function(select) {
		var visiblePassword = $('_password') || $("_transactionPassword");
		Event.observe(select, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				event = event || window.event;
				Event.stop(event);
				setFocus(visiblePassword);
			}			
		});

		select.onchange = function() {
			updateCustomFields(getValue(select));
			if (select.selectedIndex != 0) {
				updateTransactionPassword(getValue("transferType"));
			}
		}		
	},
	
	'#currencySelect': function(select) {
		select.onchange = function() {
			onCurrencyUpdate();
			setFocus("memberUsername");
		}
		Event.observe(select, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				event = event || window.event;
				Event.stop(event);
				setFocus("memberUsername");
			}			
		});
	},

	'form': function(form) {
		form.reset();
		form.onsubmit = function() {
			return requestValidation(form, null, confirmPayment);
		}
	},
	
	'#clearButton': function(button) {
		button.onclick = function() {
			button.form.reset();
			initFocus();
		}
	}
});

Event.observe(self, "load", function() {
	keyBinding(Event.KEY_ESC, $('clearButton').onclick);
	var lastReceipt = $('printLastReceipt');
	if (lastReceipt) {
		keyBinding(Event.KEY_F4, lastReceipt.onclick);
		$('printDiv').show();
		setFocus('closePrint');
		$('formTable').hide();
	} else {
		initFocus();
	}
});
