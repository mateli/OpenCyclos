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
	var showFilters = anyItemChecked();
	$('trGroupFilter')[showFilters ? 'show' : 'hide']();
	$('trAccountFeeFilters')[showFilters ? 'show' : 'hide']();
	$('trTransactionFeeFilters')[showFilters ? 'show' : 'hide']();
}

/*
* called when the member group selection is changed.
*/
memberGroupsChanged = function() {
//overrides the common version, because shouldn't do anything here
}


/*
applies additional actions when the whatToShow dropdown is changed. 
*/
var superUpdateWhatToShow = updateWhatToShow;
updateWhatToShow = function () {
	superUpdateWhatToShow;
	var whatToShow = getValue("whatToShow");
	var showDistribution = false;
	if (whatToShow == "DISTRIBUTION") {
		showDistribution = true;
	}
	$('volumeTR')[showDistribution ? 'hide' : 'show']();
	$('numberOfMembersTR')[showDistribution ? 'hide' : 'show']();
	$('maxMemberTR')[showDistribution ? 'hide' : 'show']();
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
				printResults(form, pathPrefix + "/statisticsTaxesPrintable");
			}
		}
	}
	
});
