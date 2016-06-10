var brokered = {};

function confirmAddMember(params) {
	if (params.returnValue) {
		try {
			var currentBroker = params.xml.getElementsByTagName("currentBroker").item(0).firstChild.data;
			var newBroker = params.xml.getElementsByTagName("newBroker").item(0).firstChild.data;
			var member = params.xml.getElementsByTagName("member").item(0).firstChild.data;
			var message = isEmpty(currentBroker) ? confirmationWithoutBroker : confirmationWithBroker;
			message = replaceAll(message, "#oldBroker#", currentBroker)
			message = replaceAll(message, "#newBroker#", newBroker)
			message = replaceAll(message, "#member#", member)
			if (!confirm(message)) {
				return false;
			}
		} catch (exception) {
		alert(debug(exception))
		}
	}
}

Behaviour.register({
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editBrokering?id=" + img.getAttribute("brokeringId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			var id = img.getAttribute("memberId");
			var member = brokered[id];

			//Prepare the form for a member removing
			var form = document.forms.removeBrokeredMemberForm;
			form.memberId.value = id;
			form.comments.value = '';
			form.username.value = member.username;
			form.name.value = member.name;
			
			$('removeMemberDiv').show();
			form.comments.focus();
		}
	},
	
	'#cancelRemoveMemberButton': function(button) {
		button.onclick = function() {
			$('removeMemberDiv').hide();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + button.getAttribute("memberId");
		}
	},
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printBrokerings");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportBrokeringsToCsv");
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true}, 'changeMemberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true}, 'changeMemberId', 'memberUsername', 'memberName');
	},
	
	'#addMemberForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form, null, confirmAddMember);
		}
	},
	
	'#removeMemberForm': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'textarea': function(textarea) {
		new SizeLimit(textarea, 4000);
	}
	
});