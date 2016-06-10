/*
 * updates the fields' defaults if needed. 
 */
function updateChargeFields() {
	var aRateRelation = getValue("simulation(aRateRelation)");
	var isLinear = aRateRelation == 'LINEAR';
	var isAsymptotical = aRateRelation == 'ASYMPTOTICAL';

	var chargeType = getValue("simulation(chargeType)");
	var isARate = chargeType == 'A_RATE';
	var isMixedADRates = chargeType == 'MIXED_A_D_RATES';

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
	
	$('percentRangeStart')[isMixedADRates ? 'show' : 'hide']();
	$('percentRangeEnd')[isMixedADRates ? 'show' : 'hide']();
}

/*
 * updates the fields' default including the range. 
 */
function updateChargeFieldsIncludingRange() {
	updateChargeFields();
	var chargeType = getValue("simulation(chargeType)");
	var isMixedADRates = chargeType == 'MIXED_A_D_RATES';
	
	// range must default from 0 to 100 for mixed A/D rates; else for 0 to 30
	if (isMixedADRates) {
		setValue('endA', "100");
	} else {
		setValue('endA', "30");
	}
}


/*
 * Disables an element bij setting InputBoxDisabled as a css classtype, if the flag is true, 
 * or enables the element by setting InputBoxEnabled as a css classtype if the flag is false. 
 */
function setDisableElement(element, flag) {
	if (flag) {
		//disable
		element.disabled = true;
		element.removeClassName("InputBoxEnabled");
		element.addClassName("InputBoxDisabled");
		element.readOnly = true;
	} else {
		//enable
		element.disabled = false;
		element.removeClassName("InputBoxDisabled");
		element.addClassName("InputBoxEnabled");
		element.readOnly = false;
	}
}



Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/simulations";
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#ttSelect': function(select) {
		select.onchange = function() {
			setValue("reloadData", "true");
			select.form.submit();
		}
	}, 
	
	'#feeSelect': function(select) {
		select.onchange = function() {
			setValue("reloadData", "true");
			select.form.submit();
		}
	},
	
	'.chargeTypeRadio': function(radio) {
		radio.onclick = updateChargeFieldsIncludingRange;
	},
	
	'.aRateRelation': function(radio) {
		radio.onclick = updateChargeFieldsIncludingRange;
	},
	
	'#fInfinite': function(input) {
		input.mask.changeFunction = updateChargeFieldsIncludingRange;
	}

});

Event.observe(self, "load", function() {
	updateChargeFields();
	setDisableElement($('h'), false);
	setDisableElement($('fInfinite'), false);
	setDisableElement($('aFIsZero'), false);
	setDisableElement($('gFIsZero'), false);
	setDisableElement($('f1'), false);
	setDisableElement($('fMinimal'), false);
});


