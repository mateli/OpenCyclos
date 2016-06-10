function isInsert() {
	var id = parseInt(getValue("certificationId"));
	return (isNaN(id) || id == 0);
}

function afterSelectingBuyer(member) {
	setValue("buyerId", member.id);
}

function refreshGuarantees(pageNumber) {
	self.location = pathPrefix + "/editCertification?certificationId=" + getValue("certificationId") + "&guaranteesPage=" + pageNumber;
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			backToLastLocation($H());
		}
	},

	'#lockButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/lockCertification?certificationId=" + button.getAttribute("certificationId");
		}
	},

	'#unlockButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/unlockCertification?certificationId=" + button.getAttribute("certificationId");
		}
	},

	'#cancelButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/cancelCertification?certificationId=" + button.getAttribute("certificationId");
		}
	},

	'#deleteButton': function(button) {
		button.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/deleteCertification?certificationId=" + button.getAttribute("certificationId");
			}
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},

	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('certification(guaranteeType)').focus();
		}
	},
	
	'#buyerUsername': function(input) {
		var div = $('buyersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, groupIds: buyerGroupIds}, 'buyerId', 'buyerUsername', 'buyerName', 'beginDate', afterSelectingBuyer);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#buyerName': function(input) {
		var div = $('buyersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, groupIds: buyerGroupIds}, 'buyerId', 'buyerUsername', 'buyerName', 'beginDate', afterSelectingBuyer);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},

	'img.guaranteeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/guaranteeDetails?guaranteeId=" + img.getAttribute("guaranteeId");
		}
	}
	
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
});