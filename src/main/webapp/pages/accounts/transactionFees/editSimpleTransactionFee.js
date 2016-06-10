function isInsert() {
	var id = parseInt(getValue("transactionFee(id)"));
	return (isNaN(id) || id == 0);
}

function updateFromSubject() {
	var fromFixedMember = getValue("transactionFee(payer)") == "FIXED_MEMBER";
	$$(".fromFixedMember").each(function(element) {
		element[fromFixedMember ? 'show' : 'hide']();
	});
}

function updateToSubject() {
	var toFixedMember = getValue("transactionFee(receiver)") == "FIXED_MEMBER";
	$$(".toFixedMember").each(function(element) {
		element[toFixedMember ? 'show' : 'hide']();
	});
}

function updateFromGroups() {
	var check = $('fromAllCheck');
	if (!check) return;
	var tr = $('trFromGroups');
	if (check.checked) {
		tr.hide();
	} else {
		tr.show();
		fromGroupsSelect.updateValues();
	}
}

function updateToGroups() {
	var check = $('toAllCheck');
	if (!check) return;
	var tr = $('trToGroups');
	if (check.checked) {
		tr.hide();
	} else {
		tr.show();
		toGroupsSelect.updateValues();
	}
}

function updateAvailableChargeTypes() {
	var fromMember = getValue("transactionFee(payer)") == "SOURCE";
	var toSystem = ["DESTINATION", "SYSTEM"].include(getValue("transactionFee(receiver)"));
	var includeARate = allowARate && fromMember && toSystem;
	var includeDRate = allowDRate && fromMember && toSystem;
	
	var possibleChargeTypes = chargeTypes.select(function(type) {
		switch (type.name) {
			case 'A_RATE':
				return includeARate;
			case 'D_RATE':
				return includeDRate;
			case 'MIXED_A_D_RATES':
				return includeARate && includeDRate;
			default:
				return true;
		}
	});
	setOptions('chargeTypeSelect', possibleChargeTypes, false, false, 'label', 'name');
	updateChargeFields();
}

function updateChargeFields() {
	var aRateRelation = getValue("transactionFee(aRateRelation)");
	var isLinear = aRateRelation == 'LINEAR';
	var isAsymptotical = aRateRelation == 'ASYMPTOTICAL';

	var chargeType = getValue("chargeTypeSelect");
	var isFixed = chargeType == 'FIXED';
	var isPercentage = chargeType == 'PERCENTAGE';
	var isARate = chargeType == 'A_RATE';
	var isDRate = chargeType == 'D_RATE';
	var isMixedADRates = chargeType == 'MIXED_A_D_RATES';

	// When any of the rates, there deduct is not visible and assumed as yes
	var isAnyRate = isARate || isDRate || isMixedADRates;
	$('deductionTR')[isAnyRate ? 'hide' : 'show']();
	
	// Value is visible only when is fixed or percentage
	$('valueTR')[isFixed || isPercentage ? 'show' : 'hide']();
	$('percentSign')[isPercentage ? 'show' : 'hide']();
	
	// Update the A-Rate parameters
	var aRateParameters = $("aRateParameters");
	if (!isEmpty(aRateParameters)) {
		var fInfinite = null;
		
		// F-Infinite is visible when A-Rate relation is asymptotical 
		var showFInfinite = isAsymptotical;
		if (showFInfinite) {
			fInfinite = $('fInfinite').mask.getAsNumber();
		} else {
			setValue('fInfinite', '');
		}
		$('fInfiniteRow')[showFInfinite ? 'show' : 'hide']();
		
		// A(F=0) is visible when A rate only and F-Infinite is null or < 0
		var showAFIsZero = isARate && (isLinear || (fInfinite != null && fInfinite < 0));
		$('aFIsZeroRow')[showAFIsZero ? 'show' : 'hide']();
		if (!showAFIsZero) {
			setValue('aFIsZero', '');
		}
		
		// F-1 is visible when A rate only and F-Infinite >= 0
		var showF1 = isARate && fInfinite != null && fInfinite >= 0;
		$('f1Row')[showF1 ? 'show' : 'hide']();
		if (!showF1) {
			setValue('f1', '');
		}
		
		// G(F=0) is visible only in mixed A/D rates
		$('gFIsZeroRow')[isMixedADRates ? 'show' : 'hide']();
		if (!isMixedADRates) {
			setValue('gFIsZero', '');
		}
		
		// Show / hide the entire parameters section
		var showARateParameters = isARate || isMixedADRates;
		aRateParameters[showARateParameters ? 'show' : 'hide']();
	}
}

function updateGeneratedTypes() {
	var anyAccount = $("allowAnyAccount");
	var selectedGeneratedType = getValue("transactionFee(generatedTransferType)");
	var params = $H();
	params.set('url', pathPrefix + "/listGeneratedTypesForTransactionFee");
	params.set('nature', 'SIMPLE');
	if (anyAccount) {
		params.set('allowAnyAccount', anyAccount ? anyAccount.checked : true);
	}
	params.set('transferTypeId', getValue("transferTypeId"));
	params.set('payer', getValue("transactionFee(payer)"));
	params.set('receiver', getValue("transactionFee(receiver)"));
	params.set('direction', true);
	findTransferTypes(params, function(tts) {
		setOptions('generatedSelect', tts, false, false, 'name', 'id');
		setValue('generatedSelect', selectedGeneratedType);
	});
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editTransferType?accountTypeId=" + getValue("accountTypeId") + "&transferTypeId=" + getValue("transferTypeId");
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
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form, ['transferTypeName', 'payerText', 'receiverText', 'generatedTransferTypeName', 'fromFixedMemberText', 'toFixedMemberText']);
			getObject('transactionFee(name)').focus();
		}
	},
	
	'#payerSelect': function(select) {
		select.onchange = function() {
			updateGeneratedTypes();
			updateFromSubject();
			if (isInsert()) {
				updateAvailableChargeTypes();
			}
		}
	},
	
	'#receiverSelect': function(select) {
		select.onchange = function() {
			updateGeneratedTypes();
			updateToSubject();
			if (isInsert()) {
				updateAvailableChargeTypes();
			}
		}
	},
	
	'#allowAnyAccount': function(checkbox) {
		checkbox.onchange = updateGeneratedTypes;
	},
	
	'#fromAllCheck': function(checkbox) {
		checkbox.onclick = updateFromGroups;
	},
	
	'#toAllCheck': function(checkbox) {
		checkbox.onclick = updateToGroups;
	},

	'#fromFixedMemberUsername': function(input) {
		var div = $('fromFixedMembersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'fromFixedMemberId', 'fromFixedMemberUsername', 'fromFixedMemberName');
	},
	
	'#fromFixedMemberName': function(input) {
		var div = $('fromFixedMembersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'fromFixedMemberId', 'fromFixedMemberUsername', 'fromFixedMemberName');
	},

	'#toFixedMemberUsername': function(input) {
		var div = $('toFixedMembersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'toFixedMemberId', 'toFixedMemberUsername', 'toFixedMemberName');
	},
	
	'#toFixedMemberName': function(input) {
		var div = $('toFixedMembersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'toFixedMemberId', 'toFixedMemberUsername', 'toFixedMemberName');
	},
	
	'#chargeTypeSelect': function(select) {
		select.onchange = updateChargeFields;
	},
	
	'.aRateRelation': function(radio) {
		radio.onclick = updateChargeFields;
	},
	
	'#fInfinite': function(input) {
		input.mask.changeFunction = updateChargeFields;
	}
});

Event.observe(self, "load", function() {
	updateFromSubject();
	updateToSubject();
	updateGeneratedTypes();
	updateFromGroups();
	updateToGroups();
	updateChargeFields();
	if (isInsert()) {
		updateAvailableChargeTypes();
		enableFormForInsert();
	}
});
