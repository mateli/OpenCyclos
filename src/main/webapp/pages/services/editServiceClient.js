function updateFields(atload) {
	var memberId = getValue('memberId');
	var restrictedToMember = !isEmpty(memberId);
	var channel = channelsById[getValue('channelSelect')];
	var trAccess = $('tr_ACCESS');
	var trAdvertisements = $('tr_ADVERTISEMENTS');
	var trMembers = $('tr_MEMBERS');
	var trManageMembers = $('tr_MANAGE_MEMBERS');
	var trWebShop = $('tr_WEBSHOP');
	var trDoPayment = $('tr_DO_PAYMENT');
	var trReceivePayment = $('tr_RECEIVE_PAYMENT');
	var trChargebackPayment = $('tr_CHARGEBACK');
	var trCredentialsRequired = $('tr_credentials_required');
	$$('.operationTR').each(function(tr) {
		var visible;
		if (isEmpty(channel)) {
			//Empty channel hides webshop, do payment, receive payment, chargeback payment access information
			visible = tr != trWebShop && tr != trDoPayment && tr != trReceivePayment && tr != trChargebackPayment && tr != trAccess && tr != trManageMembers;
			if (visible && restrictedToMember) {
				//Enforce that when restricted, the manage members permission won't be used
				visible = tr != trManageMembers;
			}
		} else if (channel == 'webshop') {
			//Webshop channel only allows webshop operation
			visible = tr == trWebShop;
		} else {
			//Other channels always hides webshop and hides receive payment when there's no member
			visible = tr != trWebShop;
			if (visible) {
				if (restrictedToMember) {
					visible = tr != trManageMembers;
				} else {
					visible = tr != trReceivePayment;
				}
			}
		}
		tr[visible ? 'show' : 'hide']();
	});

	//Invoke render on MultiDropDowns, to ensure they will be correctly drawn  
	if (trDoPayment.visible()) doPaymentTypes.render();
	if (trReceivePayment.visible()) receivePaymentTypes.render();
	if (trChargebackPayment.visible()) chargebackPaymentTypes.render();
	if (trManageMembers.visible()) manageGroups.render();

	trCredentialsRequired[isEmpty(memberId) && !isEmpty(channel) && channel != 'webshop' ? 'show' : 'hide']();
	
	// Update the ignoreValidations according to the manage groups
	updateManageGroups();

	//The at load event, will only show / hide fields, because the expected TTs are already there
	if (atload) {
		return;
	}
	
	//Update the do payment types
	if (trDoPayment.visible()) {
		var params = $H();
		params.set("channel", channel);
		params.set("context", "PAYMENT");
		params.set("fromOwnerId", memberId);
		params.set("ignoreGroup", true);
		findTransferTypes(params, function(tts) {
			var values = [];
			if (tts) {
				tts.each(function(tt) {
					values.push(new Option(tt.name, tt.id));
				});
			}
			doPaymentTypes.values = values;
			doPaymentTypes.render();
			if (isEmpty(memberId)) {
				chargebackPaymentTypes.values = values;
				chargebackPaymentTypes.render();
			}
		});
	}
	
	//Update the receive payment types
	if (trReceivePayment.visible()) {
		params = $H();
		params.set("channel", channel);
		params.set("context", "PAYMENT");
		params.set("ignoreGroup", true);
		params.set("fromNature", 'MEMBER');
		params.set("toOwnerId", memberId);

		findTransferTypes(params, function(tts) {
			var values = [];
			if (tts) {
				tts.each(function(tt) {
					values.push(new Option(tt.name, tt.id));
				});
			}
			receivePaymentTypes.values = values;
			receivePaymentTypes.render();
			if (!isEmpty(memberId)) {
				chargebackPaymentTypes.values = values;
				chargebackPaymentTypes.render();
			}
		});
	} else {
		receivePaymentTypes.values = [];
		receivePaymentTypes.render();
	}
}

function updateManageGroups() {
	var showIgnoreValidations = $('tr_MANAGE_MEMBERS').visible() && !isEmpty(getValue("serviceClient(manageGroups)"));
	$('tr_ignoreRegistrationValidations')[showIgnoreValidations ? 'show' : 'hide']();
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},

	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('serviceClient(name)').focus();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchServiceClients";
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName', null, updateFields.bind(self, false));
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName', null, updateFields.bind(self, false));
	},
	
	'#channelSelect': function(select) {
		select.onchange = function() {
			updateFields(false);
		}
	}
});

Event.observe(self, "load", function() {
	if (isEmpty(getValue("serviceClient(id)"))) {
		enableFormForInsert();
	}
	updateFields(true);
	
	if (emptyPassword) {
		setValue("serviceClient(username)", "");
		setValue("serviceClient(password)", "");
	}
})