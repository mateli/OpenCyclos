function enableBuyersAndSellersPermissions() {
	canBuyWithPaymentObligationsFromGroups.enable();
	canBuyWithPaymentObligationsFromGroups.render();
	$('chk_memberGuarantees.sellWithPaymentObligations').disabled = false;
}

function disableBuyersAndSellersPermissions() {
    canBuyWithPaymentObligationsFromGroups.values.each(function(option) { option.selected = false; } );
	canBuyWithPaymentObligationsFromGroups.disable();
	canBuyWithPaymentObligationsFromGroups.render();
	$('chk_memberGuarantees.sellWithPaymentObligations').checked = false;
	$('chk_memberGuarantees.sellWithPaymentObligations').disabled = true;
}

function enableIssuersPermissions() {
	canIssueCertificationToGroups.enable();
	canIssueCertificationToGroups.render();
}

function disableIssuersPermissions() {
	canIssueCertificationToGroups.values.each(function(option) { option.selected = false; } );
	canIssueCertificationToGroups.disable();
	canIssueCertificationToGroups.render();
}

function showHideBuyersAndSellersPermissions() {
	var issuersChecked = hasIssuersPermissions();
	if (issuersChecked) {
		disableBuyersAndSellersPermissions();
	} else {
		enableBuyersAndSellersPermissions();
	} 
}

function showHideIssuersPermissions() {
	var buyersAndSellersChecked = hasBuyersAndSellersPermissions();
	if (buyersAndSellersChecked) {
		disableIssuersPermissions();
	} else {
		enableIssuersPermissions();
	}
}

function showHideAll() {
	if (typeof(guaranteeTypes) == 'undefined') {
		return;
	} 
	guaranteeTypes.render();
	
	if (groupNature == 'MEMBER') {
		canIssueCertificationToGroups.render();
		canBuyWithPaymentObligationsFromGroups.render();
	
		var issuersChecked = hasIssuersPermissions();
		var buyersAndSellersChecked = hasBuyersAndSellersPermissions();
	
		if (issuersChecked) {
			disableBuyersAndSellersPermissions();
		}
		if (buyersAndSellersChecked) {
			disableIssuersPermissions();
		}
	}
}

function hasOptionSelected(options) {
	for (var i = 0; i < options.length; i++) {
		if (options[i].checked) {
			return true;
		}
	}
	return false;
}

function hasIssuersPermissions() {
	return (hasOptionSelected(document.getElementsByName('permission(canIssueCertificationToGroups)')));
}

function hasBuyersAndSellersPermissions() {
	return (hasOptionSelected(document.getElementsByName('permission(canBuyWithPaymentObligationsFromGroups)')) || $('chk_memberGuarantees.sellWithPaymentObligations').checked);
}

function managedGroupsChanged() {
	updateAsMemberToMemberTTs();
	updateAsMemberToSelfTTs();
	updateAsMemberToSystemTTs();
	updateGrantLoanTTs();
	updateSystemToMemberTTs();
}

function systemAccountTypesChanged() {
	updateAsMemberToSystemTTs();
	updateGrantLoanTTs();
	updateSystemToMemberTTs();
	updateSystemToSystemTTs();
}

function canViewProfileOfGroupsChanged() {
	updateCanViewInformationOf();
	updateBrokerCanViewInformationOf()
}

function updateCanViewInformationOf() {
	if (typeof(canViewInformationOf) == 'undefined') return;
	var groups = ensureArray(getValue('permission(canViewProfileOfGroups)'));
	if (groups.length > 0) {
		var params = arrayToParams(groups, "memberGroupId");
		var oldValues = ensureArray(getValue('permission(canViewInformationOf)'));
		findAccountTypes(params, function(accountTypes) {
			canViewInformationOf.values = ensureArray(accountTypes).map(function(at) {
				return {text: at.name, value: at.id, selected: oldValues.include(at.id)}
			});
			canViewInformationOf.render();
		});
	} else {
		canViewInformationOf.values = [];
		canViewInformationOf.render();
	}
}

function updateBrokerCanViewInformationOf() {
	if (typeof(brokerCanViewInformationOf) == 'undefined') return;
	var groups = ensureArray(getValue('permission(canViewProfileOfGroups)'));
	if (groups.length > 0) {
		var params = arrayToParams(groups, "memberGroupId");
		var oldValues = ensureArray(getValue('permission(brokerCanViewInformationOf)'));
		findAccountTypes(params, function(accountTypes) {
			brokerCanViewInformationOf.values = ensureArray(accountTypes).map(function(at) {
				return {text: at.name, value: at.id, selected: oldValues.include(at.id)}
			});
			brokerCanViewInformationOf.render();
		});
	} else {
		brokerCanViewInformationOf.values = [];
		brokerCanViewInformationOf.render();
	}
}

function getManagesGroups(paramName) {
	return arrayToParams(getValue('permission(managesGroups)'), paramName);
}

function getSystemAccounts(paramName) {
	return arrayToParams(getValue('permission(viewInformationOf)'), paramName);
}

function updateAsMemberToMemberTTs() {
	if (typeof(asMemberToMemberTTs) == 'undefined') return;
	var params = "ignoreGroup=true&context=PAYMENT&fromNature=MEMBER&toNature=MEMBER";
	params += '&' + getManagesGroups('fromGroups');
	params += '&' + getManagesGroups('toGroups');
	var oldValues = ensureArray(getValue('permission(asMemberToMemberTTs)'));
	findTransferTypes(params, function(transferTypes) {
		asMemberToMemberTTs.values = ensureArray(transferTypes).map(function(tt) {
			return {text: tt.name, value: tt.id, selected: oldValues.include(tt.id)}
		});
		asMemberToMemberTTs.render();
	});
}

function updateAsMemberToSelfTTs() {
	if (typeof(asMemberToSelfTTs) == 'undefined') return;
	var params = "ignoreGroup=true&context=SELF_PAYMENT&fromNature=MEMBER&toNature=MEMBER";
	params += '&' + getManagesGroups('fromGroups');
	params += '&' + getManagesGroups('toGroups');
	var oldValues = ensureArray(getValue('permission(asMemberToSelfTTs)'));
	findTransferTypes(params, function(transferTypes) {
		asMemberToSelfTTs.values = ensureArray(transferTypes).map(function(tt) {
			return {text: tt.name, value: tt.id, selected: oldValues.include(tt.id)}
		});
		asMemberToSelfTTs.render();
	});
}

function updateAsMemberToSystemTTs() {
	if (typeof(asMemberToSystemTTs) == 'undefined') return;
	var params = "ignoreGroup=true&context=PAYMENT&fromNature=MEMBER&toNature=SYSTEM";
	params += '&' + getManagesGroups('fromGroups');	
	params += '&' + getSystemAccounts('toAccountTypeId');
	var oldValues = ensureArray(getValue('permission(asMemberToSystemTTs)'));
	findTransferTypes(params, function(transferTypes) {
		asMemberToSystemTTs.values = ensureArray(transferTypes).map(function(tt) {
			return {text: tt.name, value: tt.id, selected: oldValues.include(tt.id)}
		});
		asMemberToSystemTTs.render();
	});
}

function updateGrantLoanTTs() {
	if (typeof(grantLoanTTs) == 'undefined') return;
	var params = "ignoreGroup=true&context=LOAN";
	params += '&' + getSystemAccounts('fromAccountTypeId');
	params += '&' + getManagesGroups('toGroups');
	var oldValues = ensureArray(getValue('permission(grantLoanTTs)'));
	findTransferTypes(params, function(transferTypes) {
		grantLoanTTs.values = ensureArray(transferTypes).map(function(tt) {
			return {text: tt.name, value: tt.id, selected: oldValues.include(tt.id)}
		});
		grantLoanTTs.render();
	});
}

function updateSystemToMemberTTs() {
	if (typeof(systemToMemberTTs) == 'undefined') return;
	var params = "ignoreGroup=true&context=PAYMENT";
	params += '&fromNature=SYSTEM';
	params += '&toNature=MEMBER';
	params += '&' + getSystemAccounts('fromAccountTypeId');
	params += '&' + getManagesGroups('toGroups');
	var oldValues = ensureArray(getValue('permission(systemToMemberTTs)'));
	findTransferTypes(params, function(transferTypes) {
		systemToMemberTTs.values = ensureArray(transferTypes).map(function(tt) {
			return {text: tt.name, value: tt.id, selected: oldValues.include(tt.id)}
		});
		systemToMemberTTs.render();
	});
	
}

function updateSystemToSystemTTs() {
	if (typeof(systemToSystemTTs) == 'undefined') return;
	var params = "ignoreGroup=true&fromNature=SYSTEM";
	params += '&toNature=SYSTEM';
	params += '&' + getSystemAccounts('fromAccountTypeId');
	params += '&' + getSystemAccounts('toAccountTypeId');
	var oldValues = ensureArray(getValue('permission(systemToSystemTTs)'));
	findTransferTypes(params, function(transferTypes) {
		systemToSystemTTs.values = ensureArray(transferTypes).map(function(tt) {
			return {text: tt.name, value: tt.id, selected: oldValues.include(tt.id)}
		});
		systemToSystemTTs.render();
	});
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listGroups";
		}
	},
	
	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			showHideAll();
		}
	},
	
	'#groupSettingsButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editGroup?groupId=" + getValue("groupId");
		}
	},
	
	'.permissionCheck': function(check) {
		check.onclick = function() {
			var id = check.getAttribute("operationId");
			$('op_selected_' + id).value = check.checked;
		}
	}
});
Event.observe(self, "load", function() {
	if (groupNature == 'MEMBER') { 
		showHideAll();
		$('chk_memberGuarantees.sellWithPaymentObligations').onclick = showHideIssuersPermissions;		
	}
});