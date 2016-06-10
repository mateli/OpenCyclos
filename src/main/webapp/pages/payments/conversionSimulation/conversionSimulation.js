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
	} else {
		//enable
		element.disabled = false;
		element.removeClassName("InputBoxDisabled");
		element.addClassName("InputBoxEnabled");
	}
}


/*
 * updates the rated section: if the transfer type doesn't have any rated fees, the
 * rated section is not shown.  
 */
function updateRated() {
	var selectedTTId = getValue("simulation(transferType)");
	var selectedTT = tts.find(function (tt) { return tt.id == selectedTTId; });
	var showRated = selectedTT && selectedTT.rated;
	/*for some reason, showRated as a condition is always evaluated to true, 
	 * even if the value is "false".
	 * So therefore showRated=="true" is used as condition. 
	 * Is showRated interpreted as a string in stead of as a boolean??
	 */
	$('ratedBlock')[showRated=="true" ? 'show' : 'hide']();
	var advanced = "true" == getValue("advanced");
	if (advanced) {
		//only show d-rate if tt has d-rated fees
		var showDRated = selectedTT && selectedTT.drated;
		$('trDRate')[showDRated=="true" ? 'show' : 'hide']();
		//only show a-rate if tt has a-rated fees
		var showARated = selectedTT && selectedTT.arated;
		$('trARate')[showARated=="true" ? 'show' : 'hide']();
	}
	// it makes no sense to have advanced or simple form if no rated tt's, because then everything is simple. 
	$('advancedButtons')[showRated=="true" ? 'show' : 'hide']();
}

/*
 * If the useActualRates checkbox is checked, the rates are shown, but cannot
 * be adapted. 
 */
function updateUseActualRates() {
	var advanced = "true" == getValue("advanced");
	if (advanced) {
		var useActualRatesSelected = booleanValue(getValue("useActualRatesBox"));
		setDisableElement($('aRateEdit'), useActualRatesSelected);
		setDisableElement($('dRateEdit'), useActualRatesSelected);
		$('targetedA')[useActualRatesSelected ? 'hide' : 'show']();
		$('presentA')[useActualRatesSelected ? 'show' : 'hide']();
		$('targetedD')[useActualRatesSelected ? 'hide' : 'show']();
		$('presentD')[useActualRatesSelected ? 'show' : 'hide']();
		$('presentRateForDate')[useActualRatesSelected ? 'show' : 'hide']();
		//if useActualRatesSelected = true, set values back to original rates
		if (useActualRatesSelected) {
			if (arateDefault) {
				$('aRateEdit').mask.setAsNumber(arateDefault);
			}
			if (drateDefault) {
				$('dRateEdit').mask.setAsNumber(drateDefault);
			}
		}
	} else {
		$('presentRateForDate')['show']();
	}
}

/*
 * function adds an 'advanced=true/false' param to the parameter part of the url.
 * param paramstring: the parameter part of the url, starting at the ? sign. If there are no parameters yet,
 *     this is an empty string
 * param advanced: a boolean indicating the PRESENT state of the form. The url will get the NEGATED value of this boolean.
 * 
 * return: the parameter part of the url with a "advanced=true/false" param attached to it. If the advanced param
 * already existed, it is not added again, but replaced by the negated version.
 */
function addAdvancedParameter(paramString, advanced) {
	var resultString = paramString;
	if (paramString.indexOf("advanced") > -1) {
		/* location does already have an advanced param which must be stripped */
		var stopindex = paramString.indexOf("advanced");
		/* we are assuming that advanced is the last param */
		paramString = paramString.substring(0, stopindex - 1);
	}
	if (paramString.indexOf("?") == -1) {
		/* location does not contain params section yet */
		resultString = "?advanced=" + (!advanced);
	} else {
		/* location does have other params yet */
		resultString = paramString + "&advanced=" + (!advanced);
	}
	return resultString;
}

/*
 * function assures the correct params for a call via location=....
 * This function takes care that the "memberId=.." param is attached to the parameter part of the url.
 * As the location=... method does not use hidden parameters to pass values, all values must be passed via params, so
 * we need this memberId param.
 * As the memberId param must be inserted before the advanced param, it calls the addAdvancedParameter function first. 
 * 
 * For params description see addAdvancedParameter function.
 * 
 */
function assureParams(paramString, advanced) {
	paramString = addAdvancedParameter(paramString, advanced);
	var memberId = getValue("memberId");
	if (memberId <= 0 || isOperator || paramString.indexOf("memberId") > -1) {
		return paramString;
	}
	advancedIndex = paramString.indexOf("advanced");
	var firstPart = paramString.slice(0,advancedIndex);
	var lastPart = paramString.slice(advancedIndex);
	var memberParam = "memberId=" + memberId + "&";
	return firstPart + memberParam + lastPart;
}


Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},
	
	'#accountSelect': function(select) {
		select.onchange = function() {
			setValue("reloadData", "true");
			select.form.submit();
		}
	},
	
	'#transferTypeSelect': function(select) {
		select.onchange = updateRated;
	},
	
	'#useActualRatesBox': function(select) {
		select.onchange = updateUseActualRates;
	},
	
	'#modeButton': function(button) {
		button.onclick = function() {
			var advanced = "true" == getValue("advanced");
			var appendString = assureParams(self.location.search, advanced);
			self.location = self.location.pathname + appendString;
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	}
});


Event.observe(self, "load", function() {
	updateRated();
	updateUseActualRates();
});
