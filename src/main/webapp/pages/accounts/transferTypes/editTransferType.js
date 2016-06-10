function isInsert() {
	var id = parseInt(getValue("transferTypeId"));
	return (isNaN(id) || id == 0);
}

function updateAccountTypes() {
	setOptions('toSelect', accountTypes, false, false, 'name', 'id');
}

function selectedAccountType() {
	var selected = getValue('toSelect');
	if (selected) {
		return accountTypes.find(function(at) {return at.id == selected});
	} else {
		return null;
	}
}

function updateToMemberFields() {
	var toMember;
	if (isInsert()){
		var toId = getValue("transferType(to)");
		var to = accountTypes.find(function (at) { return at.id == toId; });
		toMember = to && !to.isSystem;
	} else {
		toMember = !isToSystemAccount;
	}
	$$(".toMember").each(function(e) {
		e[toMember ? "show" : "hide"]();
		if (!toMember) {
			//When not to member, clear the values
			$A(e.getElementsByTagName("input")).each(function(input) {
				setValue(input, "");
			});
		}
	});
}

function updateAllFields(onLoad) {
	updateLoanFields(onLoad);
	updateFeedbackFields(onLoad);
	updateEnabledFields(onLoad);
	updateAvailabilityFields(onLoad);
	updateToMemberFields(onLoad);
	updateSchedulingFields(onLoad);
}

function updateSchedulingFields(onLoad) {
	var enabled = booleanValue(getValue("schedulingCheck"));
	$$(".trScheduling").each(function(tr) {
		tr[enabled ? 'show' : 'hide']();
	});
}

function updateEnabledFields(onLoad) {
	var showEnabled;
	if (isSystemAccount) {
		showEnabled = true;
	} else {
		var sameAccounts = getValue("transferType(from)") == getValue("transferType(to)");
		var selected = selectedAccountType();
		showEnabled = isToSystemAccount || (selected != null && selected.isSystem) || sameAccounts;
	}
	$('trEnabled')[showEnabled ? 'show' : 'hide']();
	$('trAvailability')[!showEnabled ? 'show' : 'hide']();
}

function updateLoanFields(onLoad) {
	var checkbox = getObject("isLoan");
	if (isEmpty(checkbox)) {
		return;
	}
	var showLoan;
	var isLoan;
	if (isInsert()) {
		var at = selectedAccountType();
		
		showLoan = isSystemAccount && !at.isSystem;
		isLoan = showLoan && checkbox.checked;
		
	} else {
		showLoan = true;
		isLoan = checkbox.checked;
	}
	Element[showLoan ? 'show' : 'hide']('trIsLoan');
	
	var isLoan = showLoan && checkbox.checked;
	
	Element[isLoan ? 'show' : 'hide']('trLoan');
	
	$$('.notForLoan').each(Element[isLoan ? 'hide' : 'show']);
	if (isLoan) {
		var toHide = [];
		var toShow = [];
		var selectedValue = getValue("loanTypeSelect") || "SINGLE_PAYMENT";
		switch (selectedValue) {
			case 'SINGLE_PAYMENT':
				toShow = toShow.concat(document.getElementsBySelector('.trLoanSinglePayment'));
				toHide = toHide.concat(document.getElementsBySelector('.trLoanMultiPayment'));
				toHide = toHide.concat(document.getElementsBySelector('.trLoanWithInterest'));
				break;
			case 'MULTI_PAYMENT':
				toShow = toShow.concat = document.getElementsBySelector('.trLoanMultiPayment');
				toHide = toHide.concat(document.getElementsBySelector('.trLoanSinglePayment'));
				toHide = toHide.concat(document.getElementsBySelector('.trLoanWithInterest'));
				break;
			case 'WITH_INTEREST':
				toShow = toShow.concat = document.getElementsBySelector('.trLoanWithInterest');
				toHide = toHide.concat(document.getElementsBySelector('.trLoanSinglePayment'));
				toHide = toHide.concat(document.getElementsBySelector('.trLoanMultiPayment'));
				break;
		}
		toHide.each(function(element) {
			Element.hide(element);
		})
		toShow.each(function(element) {
			Element.show(element);
		})
		if (selectedValue != 'WITH_INTEREST') {
			setValue('monthlyInterestRepaymentTypeSelect', null);
			setValue('grantFeeRepaymentTypeSelect', null);
			setValue('expirationFeeRepaymentTypeSelect', null);
			setValue('expirationDailyInterestRepaymentTypeSelect', null);
		}
		$('loanTypeSelect').disabled = false;
		setValue('schedulingCheck', false);
		if ($('requiresFeedbackCheck')) {
		    setValue('requiresFeedbackCheck', false);
		}
	} else {
		$('loanTypeSelect').disabled = true;
	}
	
	var typeSelect = getObject("loanTypeSelect");
	if (typeSelect) {
		if (!isLoan) {
			typeSelect.selectedIndex = -1;
		} else if (typeSelect.selectedIndex < 0) {
			typeSelect.selectedIndex = 0;
		}
	}
}

function updateFeedbackFields() {
	var allowChoice = false; 
	if (isInsert()) {
		var at = selectedAccountType();
		allowChoice = !at.isSystem;
	} else {
		allowChoice = !isToSystemAccount;
	}
	var trRequiresFeedback = $('trRequiresFeedback')
	if (trRequiresFeedback) {
		trRequiresFeedback[allowChoice ? 'show' : 'hide']()
		var requiresFeedback = allowChoice && $('requiresFeedbackCheck').checked;
		$$("tr.feedbackRequired").each(function(tr) {
			tr[requiresFeedback ? 'show' : 'hide']();
		});
	}
}

function updateAvailabilityFields() {
	var availabilitySelect = $('availabilitySelect');
	if (!availabilitySelect) {
		return;
	}
	var showChannels = false;
	if ($('trAvailability').visible()) {
		switch (getValue(availabilitySelect)) {
			case 'DISABLED':
				setValue('paymentContext', "false");
				setValue('selfPaymentContext', "false");
				break;
			case 'PAYMENT':
				setValue('paymentContext', "true");
				setValue('selfPaymentContext', "false");
				showChannels = true;
				break;
			case 'SELF_PAYMENT':
				setValue('paymentContext', "false");
				setValue('selfPaymentContext', "true");
				break;
		}
	} else {
		showChannels = true;
	}
	var trChannels = $('trChannels');
	if (trChannels) {
		if (showChannels) {
			trChannels.show();
			channelsSelect.render();
		} else {
			trChannels.hide();
		}
	}
	updateAllowsScheduledPaymentField();
}

function updateAllowsScheduledPaymentField() {
	var at = selectedAccountType();
	if (isToSystemAccount || (at && at.isSystem) || getValue('availabilitySelect') == 'SELF_PAYMENT') {
		setValue("transferType(allowsScheduledPayments)", false)
		$("trAllowsScheduledPayments").hide()
	} else {
		$("trAllowsScheduledPayments").show()
	}
}

function authorizerSelected() {
	var selectedValue = getValue("authorizerSelect")
	var showAdminGroups = (selectedValue == 'BROKER' || selectedValue == 'ADMIN');
	Element[showAdminGroups ? 'show' : 'hide']('trAdminGroups');
}

function cancelAuthorizationLevel() {
	Element['hide']('authorizationLevelForm');	
	cleanAuthorizationLevelForm();
}

function newAuthorizationLevel() {
	cleanAuthorizationLevelForm();
	authorizerSelected();
	Element['hide']('trLevelReadOnly');
	Element['hide']('tdAuthorizerReadOnly');
	Element['show']('tdAuthorizerSelect');
	Element['show']('authorizationLevelForm');
	var amount = getObject('authorizationLevel(amount)')
	amount.mask.setAsNumber(lastLevelAmount);
	amount.focus();
}

function cleanAuthorizationLevelForm() {
	Element['hide']('trAdminGroups');
	setValue('authorizationLevelId', '');
	setValue('authorizationLevel(id)', '');
	setValue('authorizationLevel(level)', '');
	setValue('levelReadOnly', '');
	setValue('authorizationLevel(amount)', '');
	getObject('authorizationLevel(authorizer)').selectedIndex=0;
	setValue('authorizerReadOnly','');
	setValue('authorizationLevel(adminGroups)', '');
	groupsMultiDropDown.render();
}

function editAuthorizationLevel(level) {
	cleanAuthorizationLevelForm();
	Element['show']('trLevelReadOnly');
	Element['hide']('tdAuthorizerSelect');
	Element['show']('tdAuthorizerReadOnly');
	var authorizationLevel = authorizationLevels[level - 1];
	setValue('authorizationLevelId', authorizationLevel.id);
	setValue('authorizationLevel(id)', authorizationLevel.id);
	setValue('authorizationLevel(level)', authorizationLevel.level);
	setValue('levelReadOnly', authorizationLevel.level);
	setValue('authorizationLevel(amount)', authorizationLevel.amount);
	setValue('authorizerReadOnly', authorizerNames[authorizationLevel.authorizer]);
	if (authorizationLevel.authorizer == 'BROKER' || authorizationLevel.authorizer == 'ADMIN') {
		Element['show']('trAdminGroups');
	}
	groupsMultiDropDown.values.each(function(value) {
		value.selected = authorizationLevel.adminGroups.include(value.value);
	});
	Element['show']('authorizationLevelForm');
	groupsMultiDropDown.render();
}


Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editAccountType?accountTypeId=" + getValue("accountTypeId");
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
		
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'#confirmationText': function(textarea) {
		new SizeLimit(textarea, 4000);
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['fromText', 'toText', 'authorizableText']);
			var checkBoxIsLoan = getObject('isLoan');
			if (checkBoxIsLoan && !checkBoxIsLoan.checked) {
				$('loanTypeSelect').disabled = true;
			}
			getObject('transferType(name)').focus();
		}
	},
	
	'#isLoanCheckBox': function(checkbox) {
		checkbox.onclick = function() {			
			updateLoanFields();
			updateSchedulingFields();
		}
	},
	
	'#schedulingCheck': function(checkbox) {
		checkbox.onclick = updateSchedulingFields;
	},
	
	'#loanTypeSelect': function(select) {
		select.onchange = updateLoanFields;
	},
	
	'#toSelect': function(select) {
		select.onchange = updateAllFields;
	},
	
	'#authorizerSelect': function(select) {
		select.onchange = authorizerSelected;
	},
	
	'#availabilitySelect': function(select) {
		select.onchange = function() {
			updateAvailabilityFields();
			updateSchedulingFields();
		}
	},
	
	'#requiresFeedbackCheck': function(checkbox) {
		checkbox.onclick = updateFeedbackFields;
	},
	
	'#cancelAuthorizationLevelButton': function(button) {
		button.onclick = function() {
			cancelAuthorizationLevel();
		}
	},
	
	'#newAuthorizationLevelButton': function(button) {
		button.onclick = function() {
			newAuthorizationLevel();
		}
	},
	
	'img.removeAuthorizationLevel': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeAuthorizationLevelConfirmation)) {
				self.location = pathPrefix + "/removeAuthorizationLevel?authorizationLevelId=" + img.getAttribute("authorizationLevelId") + "&accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId");
			}
		}
	},
	
	'img.authorizationLevelDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			editAuthorizationLevel(img.getAttribute("level"));
		}
	},
	
	'#newSimpleTransactionFeeButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editTransactionFee?nature=SIMPLE&accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId");
		}
	},

	'#newBrokerCommissionButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editTransactionFee?nature=BROKER&accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId");
		}
	},
	
	'img.transactionFeeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editTransactionFee?accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId") + "&transactionFeeId=" + img.getAttribute("transactionFeeId");
		}
	},
	
	'img.removeBrokerCommission': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeBrokerCommissionConfirmation)) {
				self.location = pathPrefix + "/removeTransactionFee?accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId") + "&transactionFeeId=" + img.getAttribute("transactionFeeId");
			}
		}
	},
	
	'img.removeTransactionFee': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeTransactionFeeConfirmation)) {
				self.location = pathPrefix + "/removeTransactionFee?accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId") + "&transactionFeeId=" + img.getAttribute("transactionFeeId");
			}
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'fixedDestinationMemberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'fixedDestinationMemberId', 'memberUsername', 'memberName');
	},
	
	
	'#newFieldButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editCustomField?nature=PAYMENT&transferTypeId=" + getValue("transferTypeId");
		}
	},
	
	'#changeFieldOrderButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/setCustomFieldOrder?nature=PAYMENT&transferTypeId=" + getValue("transferTypeId");
		}
	},
	
	'img.fieldDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editCustomField?nature=PAYMENT&fieldId=" + img.getAttribute("fieldId") + "&transferTypeId=" + transferTypeId;
		}
	},

	'img.removeField': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeFieldConfirmationMessage)) {
			    var fieldId = img.getAttribute("fieldId");
			    var transferTypeId = getValue("transferTypeId");
				self.location = pathPrefix + "/removeCustomField?nature=PAYMENT&fieldId=" + fieldId + "&transferTypeId=" + transferTypeId;
			}
		}
	},
	
	'#linkFieldButton': function(button) {
		button.onclick = function() {
			var at = $('linkedFieldAccountType');
			setValue(at, getValue('accountTypeId'));
			at.onchange();
			$('linkFieldContainer').show();
		}
	},
	
	'#cancelLinkedCustomFieldButton': function(button) {
		button.onclick = function() {
			$('linkFieldContainer').hide();
		}
	},
	
	'#linkedFieldAccountType': function(select) {
		select.onchange = function() {
			var sel = this;
			var params = {
				'fromAccountTypeId': getValue(sel),
				'context': 'ANY'
			};
			findTransferTypes(params, function(tts) {
				tts = tts.reject(function(tt) {return tt.id == transferTypeId});
				setOptions('linkedFieldTransferType', tts, false, false, 'name', 'id');
				$('linkedFieldTransferType').onchange();
			});
		}
	},

	'#linkedFieldTransferType': function(select) {
		select.onchange = function() {
			var ttId = getValue(this);
			if (isEmpty(ttId)) {
				clearOptions('linkedFieldCustomField');
			} else {
				findCustomFields(ttId, function(cfs) {
					setOptions('linkedFieldCustomField', cfs, false, false, 'name', 'id');
				});
			}
		}
	},
	
	'#saveLinkedCustomFieldButton': function(button) {
		button.onclick = function() {
			var fieldId = getValue('linkedFieldCustomField');
			if (isEmpty(fieldId)) {
				return;
			}
			var params = $H();
			params.set('transferTypeId', transferTypeId);
			params.set('accountTypeId', getValue('accountTypeId'));
			params.set('customFieldId', fieldId);
			self.location = pathPrefix + "/linkPaymentCustomField?" + params.toQueryString(); 
		}
	}
});

function findCustomFields(ttId, callback) {
	new Ajax.Request(pathPrefix + "/listPaymentFieldsAjax", {
	    method: 'post',
		parameters: "transferTypeId=" + ttId,
		onSuccess: function(request, array) {
			callback(array);
		}
	});
}

Event.observe(self, "load", function() {
	if (isInsert()) {
		updateAccountTypes();
	}
	updateAllFields(true);
	if (isInsert()) {
		enableFormForInsert();
	} else {
		disableField('loanTypeSelect');
	}
});
