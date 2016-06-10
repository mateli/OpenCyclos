function memberGroupsChanged() {
	if (typeof(groupSelect) == 'undefined') return;
	var groups = ensureArray(getValue('membersTransactionsReport(memberGroups)'));
	if (groups.length > 0) {
		var params = arrayToParams(groups, "memberGroupId");
		var oldValues = ensureArray(getValue('membersTransactionsReport(accountTypes)'));
		findAccountTypes(params, function(accountTypes) {
			accountTypesSelect.values = ensureArray(accountTypes).map(function(at) {
				return {text: at.name, value: at.id, selected: oldValues.include(at.id)}
			});
			accountTypesSelect.render();
		});
	} else {
		accountTypesSelect.values = [];
		accountTypesSelect.render();	
	}
	accountTypesChanged();
}

function accountTypesChanged() {
	var params = arrayToParams(getValue('membersTransactionsReport(accountTypes)'), "accountTypeId");
	if (params != '') {
		findPaymentFilters(params, updatePaymentFilters);
	} else {
		updatePaymentFilters([]);
	}
}


function updatePaymentFilters(paymentFilters) {
	var options = paymentFilters.map(
		function(paymentFilter) {
    		return new Option(paymentFilter.accountTypeName + ' - ' + paymentFilter.name , paymentFilter.id);
		}
	);
	transactionsPFsSelect.values=options;
	transactionsPFsSelect.render();
}

/*
 * Get account type ids to build the parameters object for payment filters 
 * and member groups ajax search
 */ 
function getAccountTypes(paramName) {
	return ;
}

function updatePrintButton() {
	var isEnabled = getValue("membersTransactionsReport(detailsLevel)") == "SUMMARY";
	if (isEnabled) {
		enableField('printReportButton');
	} else {
		disableField('printReportButton');
	}
}

Behaviour.register({
	
	'#printReportButton': function(button) {
		button.onclick = function() {
			var form = document.forms[0];
			if (requestValidation(form)) {
				printResults(form, pathPrefix + "/membersTransactionsReport");
			}
		}
	},
	
	'#downloadReportButton': function(button) {
		button.onclick = function() {
			var form = document.forms[0];
			if (requestValidation(form)) {
				var detailsLevel = getValue('membersTransactionsReport(detailsLevel)');
				if (detailsLevel == 'SUMMARY') {
					submitTo(form, pathPrefix + "/membersTransactionsReportsCsv");
				} else {
					submitTo(form, pathPrefix + "/membersTransactionsDetailsCsv");
				}
				
			}
		}
	},
	
	'.detailsLevel': function(radio) {
		radio.onclick = updatePrintButton;
	}
});

Event.observe(self, "load", function() {
	memberGroupsChanged();
});
