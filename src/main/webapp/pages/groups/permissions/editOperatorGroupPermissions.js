function managedGroupsChanged() {
	updateAsMemberToMemberTTs();
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

function getManagesGroups(paramName) {
	return arrayToParams(getValue('permission(managesGroups)'), paramName);
}

function getSystemAccounts(paramName) {
	return arrayToParams(getValue('permission(viewInformationOf)'), paramName);
}

function updateAsMemberToMemberTTs() {
	if (typeof(asMemberToMemberTTs) == 'undefined') return;
	var params = "fromNature=MEMBER";
	params += "&toNature=MEMBER";
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

function updateAsMemberToSystemTTs() {
	if (typeof(asMemberToSystemTTs) == 'undefined') return;
	var params = "context=PAYMENT";
	params += "&fromNature=MEMBER";
	params += "&toNature=SYSTEM";
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
	var params = "context=LOAN";
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
	var params = "context=PAYMENT";
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
	var params = "fromNature=SYSTEM";
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