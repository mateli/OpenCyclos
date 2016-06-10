// library for common functions on the statistics forms


/////////////////////////////////BUTTON FUNCTIONS ///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////

/*
enables or disables the graph buttons, depending on the flag parameter
*/
function switchGraphButtons(flag) {
	if (flag) {
		enableField($("selectAllGraphsButton"));
		enableField($("selectNoGraphsButton"));
	} else {
		disableField($("selectAllGraphsButton"));
		disableField($("selectNoGraphsButton"));
	}
} 


////////////////////////////// CHECKBOX FUNCTIONS /////////////////////////////
///////////////////////////////////////////////////////////////////////////////

/*
adapts the graphcheckbox according to the value of the checkbox (this).
also checks if graphbuttons are needed, and if yes, shows them.
Called when an itemCheckbox is changed.
*/
function adaptGraphCheckBoxAndButtons() {
	adaptGraphCheckBox(this);
	var anyBoxChecked = anyItemChecked();
	switchGraphButtons(anyBoxChecked);
	switchCommonFields();
}


/* 
enables or disables the corresponding GraphCheckbox, 
depending if the checkbox is checked or not
*/
function adaptGraphCheckBox(checkbox) {
	var checkBoxName = String(checkbox.id);
	var itemName = checkBoxName.substr(0,checkBoxName.length-8);
	var graphCheckBoxName = itemName + "GraphCheckBox";
	if (!$(graphCheckBoxName)) {
		return;
	}
	if (checkbox.checked) {
		enableField(graphCheckBoxName);
	} else {
		disableField(graphCheckBoxName);
		$(graphCheckBoxName).checked=false;
	}
}

/*
checks if a checkbox is visible, by checking up to 4 parentNodes for a 
"display: none" style. Returns false if any such style is discovered; otherwise returns true
*/
function isCheckboxVisible(checkbox) {
	if (checkbox.parentNode.style.display == "none") {
		return false;
	}
	if (checkbox.parentNode.parentNode.style.display == "none") {
		return false;
	}
	if (checkbox.parentNode.parentNode.parentNode.style.display == "none") {
		return false;
	}
	if (checkbox.parentNode.parentNode.parentNode.parentNode.style.display == "none") {
		return false;
	}
	return true;
}

/* 
switches all item checkboxes on or off, depending on the flag; 
also adapts the visability of corresponding graph checkboxes and graph buttons
*/
function switchAllItemCheckboxes(flag) {
	$$('.itemCheckBox').each(function(checkbox) {
		if (!checkbox.disabed && isCheckboxVisible(checkbox)) {
	    	checkbox.checked = flag;
	    	adaptGraphCheckBox(checkbox);
	    } else {
	    	checkbox.checked = false;
	    }
	});

	if ($('graphs')) {
		$('graphs').checked=flag;
	}
	
	switchGraphButtons(flag);
	switchCommonFields(flag);
}

/*
switches all graph checkboxes on or off, depending on the flag
*/
function switchAllGraphItems(flag) {
	$$('.graphCheckBox').each(function(checkbox) {
		if (!checkbox.disabled) {
			checkbox.checked = flag;
		}
	});
}

/* 
checks if any itemcheckbox is checked. If so, returns true, else returns false
*/
function anyItemChecked() {
	var anyChecked = false;
	$$('.itemCheckBox').each(function(checkbox) {
		if (checkbox.checked) {
			anyChecked = true;
			throw $break;
		}
	}); 
	return anyChecked;
}

/* 
returns the number of itemCheckboxes checked.
*/
function itemsChecked() {
	var number = 0;
	$$('.itemCheckBox').each(function(checkbox) {
		if (checkbox.checked) {
			number++;
		}
	}); 
	return number;
}



////////////////////////////// WHAT TO SHOW DROPDOWN /////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////

/*
Called when the whatToShow dropdown is changed. It takes care that the 
correct elements are rendered according to the whatToShow dropdown. 
It also resets all checkboxes.
Specific form javascript files may override this function to apply 
additional rendering.
See the example in statisticsActivityForm.js on how to override but
still be able to call this super function.
*/
function updateWhatToShow() {
	var whatToShow = getValue("whatToShow");
	var showSinglePeriod = false;
	var showCompare2periods = false;
	var showThroughTime = false;
	var showDistribution = false;
	var showAllNoneGraphsTD = true;
	switch (whatToShow) {
		case "SINGLE_PERIOD":
			showSinglePeriod = true;
			showAllNoneGraphsTD = false;
			break;
		case "COMPARE_PERIODS":
			showCompare2periods = true;
			break;
		case "THROUGH_TIME":
			showThroughTime = true;
			switchThroughTimeRange();
			break;
		case "DISTRIBUTION":
			showDistribution = true;
			showAllNoneGraphsTD = false;
			break;
	}
	$$('td.singlePeriod').each(function(td) {td[showSinglePeriod ? 'show' : 'hide']()});
	$$('td.compare2periods').each(function(td) {td[showCompare2periods ? 'show' : 'hide']()});
	$$('td.throughTime').each(function(td) {td[showThroughTime ? 'show' : 'hide']()});
	$$('td.distribution').each(function(td) {td[showDistribution ? 'show' : 'hide']()});
	$('selectAllNoneGraphsTD')[showAllNoneGraphsTD ? 'show' : 'hide']();
	switchAllItemCheckboxes(false);
}


//////////////////////////////////////// FILTER STUFF /////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////

/*
switches the common fields on or of, depending on the value of the whatToCheck dropdown
and if checkboxes are selected at all or not. 
Each specific form should call this function, and call a child function which 
takes care the the correct common fields are shown depending on the specific
checkbox selections.
*/
function switchCommonFields() {
    var itemCheckBoxes = $$('.itemCheckBox');
    var itemChecked = false;
    for (i=0; i<itemCheckBoxes.length; i++) {
		if (itemCheckBoxes[i].checked) {
			itemChecked = true;
			break;
		}
	}
	//general filter showing
	switchFilters(itemChecked);
	switchGroup(itemChecked);
	var whatToShowValue = getValue("whatToShow");
	//Show the period box
	var tableComparedPeriods = "tableComparedPeriods";
    Element[(whatToShowValue != "THROUGH_TIME") ? 'show' : 'hide'](tableComparedPeriods);
	var tableComparedPeriodsBr = "tableComparedPeriodsBr";
	Element[(whatToShowValue != "THROUGH_TIME") ? 'show' : 'hide'](tableComparedPeriodsBr);
	//show the second period
	var showComparedPeriod = (whatToShowValue == "COMPARE_PERIODS");
	$('tdPeriodComparedTo')[showComparedPeriod ? 'show' : 'hide']();
	$('comparedPeriodsTitle').innerHTML = showComparedPeriod ? comparedPeriodsTitle : singlePeriodTitle;
	//show the thru time stuff
	switchThroughTime((whatToShowValue == "THROUGH_TIME"));
}


/*
shows or hides the complete filter box
*/
function switchFilters(flag) {
	var tId = "filterTable"; 
	Element[flag ? 'show' : 'hide'](tId);
	var tIdBr = "filterTableBr"; 
	Element[flag ? 'show' : 'hide'](tIdBr);
}

/* 
shows or hides group edits 
*/
function switchGroup(flag) {
	var trGroupFilter = "trGroupFilter";
	Element[flag ? 'show' : 'hide'](trGroupFilter);
	groupsMultiDropDown.render();
}

/* 
shows or hides Paymentfilter edits
*/
function switchPaymentFilter(flag) {
	var trId = "trPaymentFilter"; 
	Element[flag ? 'show' : 'hide'](trId);
}

/*
 * this function is called when the user selects another set of member groups
 * on the multidropdown. It updates the paymentfilters according to the selected member group.
 */ 
memberGroupsChanged = function() {
	var oldValue = getValue("query(paymentFilter)");
	var params = getMemberGroups('memberGroups');
	findPaymentFilters(params, function(paymentFilters) {
		setOptions('query(paymentFilter)', paymentFilters, allPaymentsString, false, 'name', 'id');
		setValue('query(paymentFilter)', oldValue);
	});
}

/*
 * This function is called when the filter for groups of groups changed. 
 */
groupOfGroupsChanged = function() {
	if (typeof groupsMultiDropDown != 'undefined') {
		var groupFilters = getValue("query(groupFilters)");
		var params = arrayToParams(groupFilters, "groupFilterIds") + "&" + arrayToParams(["MEMBER", "BROKER"], "natures");
		var currentlySelected = ensureArray(getValue("query(groups)")); 
		findGroups(params, function(groups) {
			groupsMultiDropDown.values = groups.map(function(group) {
				return {text:group.name, value:group.id, selected: currentlySelected.include(group.id)};
			});
			groupsMultiDropDown.render();
		});
	}
}

/*
 * get member groups ids to build the parameters object for payment filter ajax search
 */ 
function getMemberGroups(paramName) {
	return arrayToParams(getValue('query(groups)'), paramName);
}


/*
 * function executed when the selected system account is changed. 
 * It updates the paymentFilters multi drop down.
 */
systemAccountChanged = function() {
	var oldValues = ensureArray(getValue('query(paymentFilter)'));
	var accountTypeId = getValue("query(systemAccountFilter)");
	var params = "accountTypeId=" + accountTypeId;
	
	findPaymentFilters(params, function(paymentFilters) {
		paymentFiltersMultiDropDown.values = ensureArray(paymentFilters).map(function(pf) {
			return {text: pf.name, value: pf.id, selected: oldValues.include(pf.id)}
		});
		paymentFiltersMultiDropDown.render();
	});
}


////////////////////////////// THROUGH TIME FUNCTIONS ////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////

/*
renders the through time elements, depending on the flag. 
*/
function switchThroughTime(flag) {
	var tableThroughTime = "tableThroughTime";
	Element[flag ? 'show' : 'hide'](tableThroughTime);
	var tableThroughTimeBr = "tableThroughTimeBr";
	Element[flag ? 'show' : 'hide'](tableThroughTimeBr);
}

/*
renders the correct fields depending on the Through time radio buttons 
for years, quarters or months.
*/
function switchThroughTimeRange() {
	if (getValue('query(throughTimeRange)') == 'MONTH') {
		Element['hide']("tdThroughQuarters");
		Element['hide']("tdThroughYears");
		Element['show']("tdThroughMonths");
	} else if (getValue('query(throughTimeRange)') == 'QUARTER') {
		Element['hide']("tdThroughYears");
		Element['hide']("tdThroughMonths");
		Element['show']("tdThroughQuarters");
	} else { // 'query(throughTimeRange)') == 'YEAR'
		Element['hide']("tdThroughMonths");
		Element['hide']("tdThroughQuarters");
		Element['show']("tdThroughYears");
	}
}

/*
all verification is done via the service and the actions verifyForm method
*/
function verifyForm(form) {
	if (!requestValidation(form)) {
		return false;
	}
	return true;
}



/////////////////////////////// GENERAL BEHAVIOR DEFINITIONS /////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////

/*
Defines the behavior of common elements. Specific forms should
define this again for specific elements.
*/
Behaviour.register({
	'#whatToShow': function(select) {
		select.onchange = updateWhatToShow;
	},
	
	'#systemAccountSelect': function(select) {
		select.onchange = systemAccountChanged;
	},

	'input.itemCheckBox': function(checkbox) {
		Event.observe(checkbox, "click", adaptGraphCheckBoxAndButtons);
	},

	'.throughTimeRange': function(radio) {
		radio.onclick = switchThroughTimeRange;
	},
	
	'#submitButton': function(button) {
		button.onclick = function() {
			var form = document.forms[0];
			if (verifyForm(form)) {
				form.submit();
			}
		}
	},
	
	'#selectAllButton': function(button) {
		button.onclick = switchAllItemCheckboxes.bind(self, true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = switchAllItemCheckboxes.bind(self, false);
	},
	
	'#selectAllGraphsButton': function(button) {
		button.onclick = switchAllGraphItems.bind(self, true);
	},
	
	'#selectNoGraphsButton': function(button) {
		button.onclick = switchAllGraphItems.bind(self, false);
	}
});

/*
Defines the behavior when loading the form
*/
Event.observe(self, "load", function() {
	updateWhatToShow();
	memberGroupsChanged();
});