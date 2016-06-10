/*
Library for statisticsFinancesForm. 
Works in close cooperation with statisticsForms.js
*/

/*
switches common fields on or off depending on checkboxes checked.
Remember this function should always first call its super 
in statisticsForms.js.
*/
var superSwitchCommonFields = switchCommonFields;
switchCommonFields = function () {
	superSwitchCommonFields();
	var anyBoxChecked = anyItemChecked();
	var whatToShowValue = getValue("whatToShow");
	var showThroughTime = false;
	$('trGroupFilter')['hide']();
	$('trSystemAccountFilter')[anyBoxChecked ? 'show' : 'hide']();
	$('trMultiPaymentFilter')['show']();
	$('trPaymentFilter')['hide']();
	var whatToShow = getValue("whatToShow");
	if (whatToShow == "SINGLE_PERIOD") {
		var item0Checked = $$('.itemCheckBox')[0].checked;
		if (item0Checked) {
			disableField("incomeGraphCheckBox");
			disableField("expenditureGraphCheckBox");
		}
	}
}

/* 
enables or disables the corresponding GraphCheckbox, 
depending if the checkbox is checked or not.
Overrides the version in statisticsForms.js, because of special treatment of income and expenditure
*/
var superAdaptGraphCheckBox = adaptGraphCheckBox;
adaptGraphCheckBox = function(checkbox) {
	var whatToShow = getValue("whatToShow");
	if (whatToShow == "SINGLE_PERIOD") {
		var checkBoxName = String(checkbox.id);
		var itemName = checkBoxName.substr(0,checkBoxName.length-8);
		var graphCheckBoxName = itemName + "GraphCheckBox";
		if (!$(graphCheckBoxName)) {
			return;
		}
		var item0Checked = $$('.itemCheckBox')[0].checked;
		if (checkbox.checked) {
			if (item0Checked && checkBoxName != "overviewCheckBox") {
				$(graphCheckBoxName).checked = true;
			} else {
				enableField(graphCheckBoxName);
			}
		} else {
			disableField(graphCheckBoxName);
			$(graphCheckBoxName).checked=false;
		}
	} else {
		superAdaptGraphCheckBox(checkbox);
	}
}


/*
applies additional actions when the whatToShow dropdown is changed. 
Note that it overrides statisticsForms.js updateWhatToShow.
*/
updateWhatToShow = function () {
	var whatToShow = getValue("whatToShow");
	var showSinglePeriod = false;
	var showCompare2periods = false;
	var showThroughTime = false;
	switch (whatToShow) {
		case "SINGLE_PERIOD":
			showSinglePeriod = true;
			break;
		case "COMPARE_PERIODS":
			showCompare2periods = true;
			break;
		case "THROUGH_TIME":
			showThroughTime = true;
			switchThroughTimeRange();
			break;
	}
	$('singlePeriodOverviewTR')[showSinglePeriod ? 'show' : 'hide']();
	$$('td.singlePeriod').each(function(td) {td[showSinglePeriod ? 'show' : 'hide']()});
	$$('td.compare2periods').each(function(td) {td[showCompare2periods ? 'show' : 'hide']()});
	$$('td.throughTime').each(function(td) {td[showThroughTime ? 'show' : 'hide']()});
	$('selectAllNoneGraphsTD')['show']();
	switchAllItemCheckboxes(false);
}


/*
As there is no member selection for finances, the functionality of this general
function must be overridden by a do-nothing version.
*/
memberGroupsChanged = function() {
}



/*
Defines the behavior of additional elements which were not already registered
in statisticsForms.js.
Note that this does not override Behaviour.register of statisticsForms.js.
*/
Behaviour.register({
	'#printReportButton': function(button) {
		button.onclick = function() {
			var form = document.forms[0];
			if (verifyForm(form)) {
				printResults(form, pathPrefix + "/statisticsFinancesPrintable");
			}
		}
	}
	
});

/*
Defines the behavior when loading the form
*/
Event.observe(self, "load", function() {
	updateWhatToShow();
	memberGroupsChanged();
	systemAccountChanged();
});