var transferTypeChangeFunction = null;
var transferTypesByCurrency = null
var selfPayment = false;

function updateTransferTypes(tts) {
	if (isEmpty(getValue("to")) && !booleanValue(getValue("toSystem")) && !selfPayment) {
		return;
	}
	tts = tts || transferTypes;
	transferTypes = tts;
	var cell = $('typeCell');
	var showRow = false;
	cell.innerHTML = "";
	var disablePayment = false;

	//Get the transfer types by currency
	var currencySelect = getObject("currencySelect");
	if (currencySelect) {
	    transferTypesByCurrency = new Map();
	    tts.each(function(tt) {
	        var currentTTs = transferTypesByCurrency.get(tt.currencyId)
	        if (currentTTs == null) {
	            currentTTs = [];
	            transferTypesByCurrency.put(tt.currencyId, currentTTs);
	        }
	        currentTTs.push(tt);
	    });
	} else {
	    transferTypesByCurrency = null;
	}

	//Disable those currencies with no transfer types
	var currencyId = getValue("currency");
	if (currencySelect) {
		var selectedIndex = currencySelect.selectedIndex;
		for (var i = 0; i < currencySelect.options.length; i++) {
			var option = currencySelect.options[i];
			var currentCurrency = option.value;
			var currentTTs = transferTypesByCurrency.get(currentCurrency);
			option.disabled = isEmpty(currentTTs);
			$(option)[isEmpty(currentTTs) ? 'hide' : 'show']();
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
				disablePayment = true;
			}
		}
		currencySelect.show();
	} else {
	    disablePayment = isEmpty(tts);
	}
	
	//Get the transfer types for the selected currency
	if (!disablePayment) {
	    if (transferTypesByCurrency != null) {
	        tts = transferTypesByCurrency.get(currencyId);
	        if (isEmpty(tts)) {
	            disablePayment = true;
	        }
	    }
	}
	
	if (!disablePayment && tts != null) {
		if (tts.length == 1) {
			var tt = tts[0];
	
			var hidden = document.createElement("input");
			hidden.setAttribute("id", "type");
			hidden.setAttribute("type", "hidden");
			hidden.setAttribute("name", "type");
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
			select.setAttribute("id", "type");
			select.setAttribute("name", "type");
			cell.appendChild(select);
			select.onchange = function() {
				if (typeof(transferTypeChangeFunction) == 'function') {
					transferTypeChangeFunction();
				}
				updateCustomFields(getValue(this));
			}
			
			tts.each(function(tt) {
				select.options[select.options.length] = new Option(tt.name, tt.id);
			});
		}
        showRow = true;
	}
	(showRow ? Element.show : Element.hide)("typeRow");
	if (disablePayment) {
		disableField("amount");
		disableField("description");
		disableField("submitButton");
		if (typeof noTransferTypeMessage != 'undefined') {
			alert(noTransferTypeMessage);
		}
		setFocus(lastMemberFieldWithFocus)
	} else {
		enableField("amount");
		enableField("description");
		enableField("submitButton");
	}
	if (typeof(afterUpdatingTransferTypes) == 'function') {
		afterUpdatingTransferTypes(tts);
	}
	if (tts && tts.length > 0) {
		updateCustomFields(tts[0].id);
	}
}

function updateCustomFields(ttId) {
	new Ajax.Request(context + "/do/paymentCustomFields", {
	    method: 'post',
		parameters: "typeId=" + ttId,
		onSuccess: updatePaymentFieldsCallback
	})
}

function updatePast() {
	var check = $('setDateCheck');
	if (!check) return;
	var checked = check.checked;
	if (checked) {
		$('pastDate').show();
		$('dateText').focus();
	} else {
		$('pastDate').hide();
		$('dateText').value = '';
	}
	
	if ($("schedulingTypeSelect")) {
		updateSchedulingFields();
	}
}

function prepareForm(form, callback) {
	callback();
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			var callback = function() {
				if (requestValidation(form)) {
					form.submit();
				}
			};
			prepareForm(form, callback);
			return false;
		}
	},

	'#description': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'#setDateCheck': function(check) {
		check.onclick = updatePast;
	}
});

Event.observe(self, "load", function() {
	updatePast();
});