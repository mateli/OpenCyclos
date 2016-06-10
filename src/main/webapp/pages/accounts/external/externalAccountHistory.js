function editExternalTransferLocation(externalTransferId) {
	var externalAccountId = getValue("query(account)");
	var transferImportId = parseInt(getValue("query(transferImport)"));
	
	var newLocation = pathPrefix + "/editExternalTransfer?externalAccountId=" + getValue("query(account)");
	newLocation = newLocation + "&externalTransferId=" + externalTransferId;
	if (!isNaN(transferImportId) && transferImportId != 0) {
		newLocation = newLocation + "&transferImportId=" + transferImportId;
	}
	return newLocation;
}

Behaviour.register({
	
	'#toImportListButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchTransferImports?externalAccountId=" + getValue("query(account)");
		}
	},
	
	'#newPaymentButton':function(button) {
		button.onclick = function() {
			var newLocation =  pathPrefix + "/editExternalTransfer?externalAccountId=" + getValue("query(account)");
			var transferImportId = parseInt(getValue("query(transferImport"));
			if (!isNaN(transferImportId) && transferImportId != 0) {
				newLocation = newLocation + "&transferImportId=" + transferImportId;
			}
			self.location = newLocation;
		}
	},
	
	'#processPaymentsButton': function(button) {
		button.onclick = function() {
			submitTo(document.forms[0], pathPrefix + '/retrieveTransfersToProcess');
		}
	},

	'#backButton': function(button) {
		button.onclick = function() {
			var importId = getValue("transferImportId");
			var externalAccountId = getValue("accountId");
			if (!isEmpty(importId) && parseInt(importId) > 0) {
				self.location = pathPrefix + "/searchTransferImports?externalAccountId=" + externalAccountId;
			} else {
				self.location = pathPrefix + "/overviewExternalAccounts?externalAccountId=" + externalAccountId;
			}
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			//submitTo(form, pathPrefix + "/exportAccountHistoryToCsv");
		}
	},
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			//printResults(form, pathPrefix + "/printAccountHistory");
		}
	},
	
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			var externalTransferId = img.getAttribute("externalTransferId");
			self.location = editExternalTransferLocation(externalTransferId);
		}
	},
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			var externalTransferId = img.getAttribute("externalTransferId"); 
			self.location = editExternalTransferLocation(externalTransferId);
		}
	},
	
	'img.delete': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				var externalTransferId = img.getAttribute("externalTransferId");
				self.location = pathPrefix + "/changeExternalTransfer?action=DELETE&externalTransferId=" + externalTransferId + "&externalAccountId=" + getValue("accountId") + "&externalImportId=" + getValue("importId");
			}
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#selectAllButton': function(button) {
		button.onclick = checkAll.bind(self, "externalTransferId", true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = checkAll.bind(self, "externalTransferId", false);
	},
	
	'#applyActionSelect': function(select) {
		select.selectedIndex = 0;
		select.onchange = function() {
			var selected = getValue("externalTransferId");
			if (isEmpty(selected)) {
				select.selectedIndex = 0;
				alert(nothingSelectedMessage);
				return;
			}
			var action = getValue(select);
			var submit = true;
			if (action == 'DELETE') {
				submit = confirm(removeConfirmation);
			}
			if (submit) {
				select.form.submit();
			} else {
				select.selectedIndex = 0;
			}
		}
	}
	
});