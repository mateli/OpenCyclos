//Global variable used to focus the first custom field, if any
focusFirstPaymentField = true;
document.transferTypes = [];
document.maxScheduledPayments = 0;

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
	onMemberUpdate(isEmpty(member) ? null : {id:member})
}

function onMemberUpdate(member) {
    if (member.id == loggedMember) {
        member = null;
    }
	setValue("from", member == null ? "" : member.id);
	var memberNameDisplay = $('memberNameDisplay');
	if (memberNameDisplay && member != null) memberNameDisplay.value = member.name;
	setFocus("_credentials");
	var receipt = $('printLastReceipt');
	if (receipt) {
		receipt.remove();
	}
	$('transferTypeRow').hide();
	clearOptions('transferType');
	var currencySelect = getObject(currencySelect);
	var currency = getValue("currency");
	if (!isEmpty(member) && (!currencySelect || !isEmpty(currency))) {
		var params = $H();
		params.set('context', "PAYMENT");
		params.set('channel', 'posweb');
		params.set('fromOwnerId', member.id);
		params.set('currencyId', currency);
		params.set('toOwnerId', loggedMember);
		params.set('useFromGroup', true);
		params.set("scheduling", true);
		findTransferTypes(params, updateTransferTypes);
	}
	createSchedulingFields();
}

function updateTransferTypes(transferTypes) {
	if (!transferTypes || transferTypes.length == 0) {
		document.transferTypes = [];
		alert(noTransferTypeMessage);
		return;
	}
	document.transferTypes = transferTypes;
	enableField("submitButton");
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
		onTransferTypeUpdate(transferTypes[0].id);
	}
}

function onTransferTypeUpdate(ttId) {
	if (isEmpty(ttId) || !onlyNumbers(ttId)) {
		updatePaymentFieldsCallback();
	} else {
		new Ajax.Request(context + "/do/paymentCustomFields", {
		    method: 'post',
			parameters: "columnWidth=" + encodeURIComponent("35%") + "&typeId=" + ttId,
			onSuccess: updatePaymentFieldsCallback
		});
	}
	createSchedulingFields();
}

function createSchedulingFields() {
	var transferType = null;
	var member = $('memberId').member;
	var ttId = getValue('transferType');
	document.maxScheduledPayments = (!isEmpty(ttId) && !isEmpty(member)) ? parseInt(member.maxScheduledPayments, 10) : 0;
	if (document.maxScheduledPayments > 0) {
		for (var i = 0; i < document.transferTypes.length; i++) {
			var tt = document.transferTypes[i];
			if (tt.id == ttId) {
				transferType = tt;
				break;
			}
		}
		var allowsScheduling = false;
		if (transferType != null) {
			allowsScheduling = booleanValue(transferType.allowsScheduledPayments);
		}
		if (!allowsScheduling) {
			document.maxScheduledPayments = 0;
		}
	}

	var schedulingTypesToUse = [];
	if (document.maxScheduledPayments > 0) {
		schedulingTypesToUse.push(schedulingTypes[0]);
		schedulingTypesToUse.push(schedulingTypes[1]);
		if (document.maxScheduledPayments > 1) {
			schedulingTypesToUse.push(schedulingTypes[2]);
		}
		$('schedulingTypeRow').show();
	} else {
		$('schedulingTypeRow').hide();
		$$('.scheduling').each(Element.hide);
	}

	var schedulingTypeSelect = 'schedulingTypeSelect';
	setOptions(schedulingTypeSelect, schedulingTypesToUse, false, false, 'label', 'value');
	if (schedulingTypesToUse.length > 0) {
		schedulingTypeSelect.selectedIndex = 0;
	}
	updateSchedulingFields();
}

function updateSchedulingFields() {
	var schedulingType = getValue('schedulingTypeSelect');

	$$('.scheduling').each(Element.hide);

	switch (schedulingType) {
		case "SINGLE_FUTURE":
			var scheduledFor = getObject('scheduledFor');
			scheduledFor.value = dateParser.format(dateAdd(dateParser.parse(today), 1, JST_FIELD_MONTH));
			$$('.singlePayment').each(Element.show);
			scheduledFor.focus();
			break;
		case "MULTIPLE_FUTURE":
			var paymentCount = getObject('paymentCount');
			var firstPaymentDate = getObject('firstPaymentDate');
			paymentCount.value = '';
			firstPaymentDate.value = today;
			$$('.multiplePayments').each(Element.show);
			paymentCount.focus();
			break;
	}
}

function initFocus() {
	var amount = getObject("amount");
	if (amount.readonly) {
		setFocus('username');
	} else {
		setFocus(amount);
	}
}

function loadMember(principal) {
    if (isEmpty(principal)) {
        return;
    }
	var params = $H();
	params.set('channel', 'posweb');
	params.set('principalType', principalType);
	params.set('principal', principal);
	new Ajax.Request(context + "/do/posweb/loadMemberAjax", {
	    method: 'post',
		parameters: params,
		onSuccess: function(request, member) {
			if (member == null && !isEmpty(principal)) {
				setValue("from", "");
				alert(invalidPrincipalMessage);
				return;
			}
			onMemberUpdate(member);
		}
	});
}

function applyMaskForLoadMember(principalType, input) {
	var mask = new InputMask(JST_MASK_NUMBERS, input);
	mask.blurFunction = function() {
		disableField("submitButton");
		loadMember(principalType, getValue(input));
	}
}

Behaviour.register({
	'#amount': function(input) {
		input.value = '';
		Event.observe(input, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				setFocus($('currencySelect') ? "currencySelect" : "memberUsername");
			}
		});
	},

	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", maxScheduledPayments:true}, 'memberId', 'memberUsername', 'memberName', '_credentials', onMemberUpdate);
	},

	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", maxScheduledPayments:true}, 'memberId', 'memberUsername', 'memberName', '_credentials', onMemberUpdate);
	},

	'#principalTypeSelect': function(select) {
		select.onchange = function() {
			self.location = urlWithoutQueryString() + "?principalType=" + getValue(select);
		}
	},

	'#principal': function(input) {
		var keyUpFunction = function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				disableField("submitButton");
				loadMember(getValue(input));
			}
		};
		var changeFunction = function(event) {
		    setValue('memberNameDisplay', '');
		    disableField("submitButton");
			loadMember(getValue(input));
		}
		if (input.mask) {
			input.mask.keyUpFunction = keyUpFunction;
			input.mask.changeFunction = changeFunction;
		} else {
			Event.observe(input, "keyup", keyUpFunction);
			Event.observe(input, "change", changeFunction);
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

	'#transferType': function(select) {
		Event.observe(select, "keypress", function(event) {
			if (typedCode(event) == Event.KEY_RETURN) {
				Event.stop(event);
				setFocus('credentials');
			}
		});
		select.onchange = function() {
			onTransferTypeUpdate(getValue(select));
		}
	},

	'#schedulingTypeSelect': function(select) {
		select.onchange = updateSchedulingFields;
	},

	'form': function(form) {
		form.onsubmit = function() {
			var ok = requestValidation(form, null, confirmPayment);
			if (!ok) {
				setValue('credentials', '');
			}
			return ok;
		}
	},

	'#clearButton': function(button) {
		button.onclick = function() {
			button.form.reset();
			setValue('memberId', '');
			initFocus();
		}
	}
});

Event.observe(self, "load", function() {
	setValue('memberId', '');
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
	var clearFields = function() {
		['cardId', 'anyId', 'credentials'].each(function(id) {
			try {
				setValue(id, '');
			} catch (e) {}
		});
	};
	clearFields.delay(0.01);
	disableField("submitButton");
	['memberUsername', 'memberName', 'cardId', 'anyId', '_credentials'].each(function (id) {
		var field = $(id);
		if (field) {
			field.value = '';
		}
	});
});