/*
Library for statisticsKeyDevelopmentsForm. 
Works in close cooperation with statisticsForms.js
*/

/*
switches common fiels on or of depending on checkboxes checked.
Remember this function should always first call its super 
in statisticsForms.js.
*/
var superSwitchCommonFields = switchCommonFields;
switchCommonFields = function () {
	superSwitchCommonFields();
	var oneTwoOrThree = $$('.itemCheckBox')[1].checked || $$('.itemCheckBox')[2].checked || $$('.itemCheckBox')[3].checked;
	switchFilters(anyItemChecked());
	switchPaymentFilter(oneTwoOrThree); 
	switchGroup(anyItemChecked());
}

/*
enables the corresponding graphcheckbox when an itemcheckbox is checked.
As thrutime doesn't have corresponding graphcheckboxes in this form, 
it overrides the super function in statisticsForms.js, but only for thrutime.
For other options, it just calls super.
*/
var superAdaptGraphCheckBox = adaptGraphCheckBox;
adaptGraphCheckBox = function (checkbox) {
    var whatToShow = getValue("whatToShow");
    if (whatToShow == "THROUGH_TIME") {
	   	var anyBoxChecked = anyItemChecked();
    	if (anyBoxChecked) {
    		enableField("thruTimeGraphCheckBox");
		} else {
			disableField("thruTimeGraphCheckBox");
			$("thruTimeGraphCheckBox").checked=false;
		}
    } else { //this is the overridden part
        superAdaptGraphCheckBox(checkbox);
	}
}

/*
* called when the member group selection is changed.
*/
//memberGroupsChanged = function() {
//overrides the common version, because shouldn't do anything here
//}


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
				printResults(form, pathPrefix + "/statisticsKeyDevelopmentsPrintable");
			}
		}
	}
});

