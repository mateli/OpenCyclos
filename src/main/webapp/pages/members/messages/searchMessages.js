function submitAction(action, id) {
	var form = document.forms['changeMessageStatusForm'];
	setValue(form.action, action);
	var messageId = document.createElement("input");
	messageId.setAttribute("type", "hidden");
	messageId.setAttribute("name", "messageId");
	messageId.setAttribute("value", id);
	messageId = form.appendChild(messageId);
	try {
		form.submit();
	} finally {
		Element.remove(messageId);
	}
}

Behaviour.register({
	
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/sendMessage";
		}
	},
	
	'#messageBoxSelect': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},
	
	'#categoriesSelect': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},
	
	'#backButton': function(element) {
		element.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else {
				history.back();
			}
		}
	},
	
	'a.messageDetails': function(span) {
		setPointer(span);
		span.onclick = function() {
			self.location = pathPrefix + "/viewMessage?messageId=" + span.getAttribute("messageId");
		}
	},
	
	'#applyActionSelect': function(select) {
		select.onchange = function() {
			var selected = getValue("messageId");
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
	},
	
	'#modeButton': function(button) {
		button.onclick = function() {
			var advanced = "true" == getValue("advanced");
			self.location = self.location.pathname + "?advanced=" + (!advanced)
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#selectAllButton': function(button) {
		button.onclick = checkAll.bind(self, "messageId", true);
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = checkAll.bind(self, "messageId", false);
	},
	
	'img.moveToTrash': function(element) {
		setPointer(element);
		element.title = element.alt = moveToTrashTooltip; //2 assignements
		element.onclick = function() {
		    setValue("messageId", null);
			submitAction('MOVE_TO_TRASH', element.getAttribute("messageId"));
		}
	},
	
	'img.removePermanently': function(element) {
		setPointer(element);
		element.title = element.alt = removeTooltip; //2 assignements
		element.onclick = function() {
			if (confirm(removeConfirmation)) {
	            setValue("messageId", null);
				submitAction('DELETE', element.getAttribute("messageId"));
			}
		}
	}


});