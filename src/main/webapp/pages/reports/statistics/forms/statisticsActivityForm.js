/*
Library for statisticsActivityForm. 
Works in close cooperation with statisticsForms.js
*/

/* 
enables or disables the corresponding GraphCheckbox, 
depending if the checkbox is checked or not
override the function with the same sanme in statisticsForm.js
*/
adaptGraphCheckBox = function (checkbox) {
	var checkBoxName = String(checkbox.id);
	var itemName = checkBoxName.substr(0,checkBoxName.length-8);
	var postFix = "";
	var whatToShow = getValue("whatToShow");
	switch (whatToShow) {
		case "COMPARE_PERIODS":
			postFix = "CP";
			break;
		case "THROUGH_TIME":
			postFix = "TT";
			break;
	}
	var graphCheckBoxName = itemName + "GraphCheckBox" + postFix;
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
switches common fields on if depending checkboxes are checked.
Remember that this function should always first call its super 
in statisticsForms.js.
*/
var superSwitchCommonFields = switchCommonFields;
switchCommonFields = function () {
	superSwitchCommonFields();
	var whatToShowValue = getValue("whatToShow");
    var itemCheckBoxes = $$('.itemCheckBox');
	//turn irrelevant checkboxes off
	if (whatToShowValue == "DISTRIBUTION") {
		percentageNoTradeCheckBox.checked = false;
	} else {
		for (i=0; i<itemCheckBoxes.length; i++) {
			if (itemCheckBoxes[i].hasClassName('distribution')) {
				itemCheckBoxes[i].checked = false;
			}
		}
	}
	var paymentFilterBoxesNeeded = false;
    for (i=0; i<itemCheckBoxes.length; i++) {
		if (itemCheckBoxes[i].checked) {
			if (!itemCheckBoxes[i].hasClassName('logins')) {
  				paymentFilterBoxesNeeded = true;
				break;
			}
		}
	}
	switchPaymentFilter(paymentFilterBoxesNeeded); 
}

/*
applies additional actions when the whatToShow dropdown is changed. 
Note that it first calls statisticsForms.js updateWhatToShow.
*/
var superUpdateWhatToShow = updateWhatToShow;
updateWhatToShow = function () {
	superUpdateWhatToShow();
	var showNoTraders = true;
	var whatToShowValue = getValue("whatToShow");
	if (whatToShowValue == "DISTRIBUTION") {
		showNoTraders = false;
	}
	$('noTradersTR')[showNoTraders ? 'show' : 'hide']();
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
				printResults(form, pathPrefix + "/statisticsActivityPrintable");
			}
		}
	}
});
