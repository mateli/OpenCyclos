function updateFields() {
	var sendTo = getValue('sendTo');
	if (isReply) {
		prepareForCaret("bodyText");
		setCaretToStart("bodyText");
		focusRichEditor("bodyText");
		return;
	}
	if (isEmpty(sendTo)) {
		$('subjectText').focus();
		return;
	}
	var memberRows = $$('tr.memberRow');
	var groupRows = $$('tr.groupRow');
	var adminRows = $$('tr.adminRow');
	switch (sendTo) {
		case 'MEMBER':
			cleanGroups();
			memberRows.each(Element.show);
			groupRows.each(Element.hide);
			if (isAdmin) {
				var toElement = getValue("message(toMember)");
				if (!isEmpty(loggedElement) && !isEmpty(toElement)) {
					adminRows.each(Element.show);
				} else {
					adminRows.each(Element.hide);
				}
			} else {
				adminRows.each(Element.hide);
			}
			try {
				$('memberUsername').focus();
			} catch (e) {
				$('subjectText').focus();
			}
			break;
		case 'GROUP':
			cleanMemberFields();
			memberRows.each(Element.hide);
			groupRows.each(Element.show);
			var groups = groupsAsString(getValue("message(toGroups)"));
			if (!isEmpty(groups)) {
				adminRows.each(Element.show);
			} else {
				adminRows.each(Element.hide);
			}
			toGroupsSelect.render();
			break;
		case 'BROKERED_MEMBERS':
			cleanMemberFields();
			memberRows.each(Element.hide);
			groupRows.each(Element.hide);
			adminRows.each(Element.hide);
			break;
		case 'ADMIN':
			cleanMemberFields();
			memberRows.each(Element.hide);
			groupRows.each(Element.hide);
			adminRows.each(Element.show);
			$('subjectText').focus();
			break;
	}
	
	if (isAdmin) {
		updateMessageCategories();
	}
}

function updateMessageCategories() {
	var sendTo = getValue('sendToSelect');
	var toElement = getValue("message(toMember)");
	var groups = groupsAsString(getValue("message(toGroups)"));
	
	if (sendTo=='ADMIN' || (isAdmin && !isEmpty(loggedElement) && !isEmpty(toElement)) || !isEmpty(groups)) {
	
		// Ignored param, used just to build a consistent query
		var params = "?a=1";
		
		// From element
		if (!isEmpty(loggedElement)) {
			params = params + "&fromElement=" + loggedElement;
		}
		
		// To Element
		if (!isEmpty(toElement)) {
			params = params + "&toElement=" + toElement;
		}
		
		// Groups
		if (!isEmpty(groups)) {
			params = params + "&" + groupsAsString(getValue("message(toGroups)"));
		}
		
		findMessageCategories(params, updateMessageCategoriesSelect);
	} else {
		cleanMessageCategoriesSelect();
	}
}

function groupsAsString(groups) {
	var ret = [];
	if (!isEmpty(groups)) {
		if (groups instanceof Array) {
			ret = groups;
		} else {
			ret = [groups];
		}
	}
	return ret.map(function(g) {
		return "groups=" + g
	}).join("&");
}

function cleanGroups() {
	checkAll('message(toGroups)', false);
}

function cleanMemberFields() {
	$('memberId').value="";
	$('memberUsername').value="";
	$('memberName').value="";
}

function cleanMessageCategoriesSelect() {
	var cell = $('messageCategoriesCell');
	var showRow = false;
	cell.innerHTML = "";
	var select = document.createElement("select");
	select.setAttribute("id", "categorySelect");
	select.setAttribute("name", "message(category)");
	cell.appendChild(select);
}

function updateMessageCategoriesSelect(messageCategories) {
	try {
		var cell = $('messageCategoriesCell');
		if (!cell) {
			return;
		}
		cell.innerHTML = "";
		var showRow = false;
		if (messageCategories != null) {
			if (messageCategories.length == 0) {
				alert(noMessageCategories);
			} else if (messageCategories.length == 1) {
				var mc = messageCategories[0];
		
				var hidden = document.createElement("input");
				hidden.setAttribute("type", "hidden");
				hidden.setAttribute("name", "message(category)");
				hidden.setAttribute("value", mc.id);
				cell.appendChild(hidden);
				
				var text = document.createElement("input");
				text.setAttribute("class", "InputBoxDisabled large");
				text.setAttribute("readonly", "readonly");
				text.setAttribute("value", mc.name);
				cell.appendChild(text);
				
				showRow = true;
			} else {
				var select = document.createElement("select");
				select.setAttribute("id", "categorySelect");
				select.setAttribute("name", "message(category)");
				cell.appendChild(select);
				
				messageCategories.each(function(mc) {
					select.options[select.options.length] = new Option(mc.name, mc.id);
				});
				showRow = true;
			}
		}
		(showRow ? Element.show : Element.hide)("adminRow");
	} catch (e) {
		alert(debug(e));
	}
}

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'#sendToSelect': function(select) {
		select.onchange = updateFields;
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		var updateFunction = isMember ? null : updateMessageCategories;
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true}, 'memberId', 'memberUsername', 'memberName', 'subjectText', updateFunction);
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		var updateFunction = isMember ? null : updateMessageCategories;
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true}, 'memberId', 'memberUsername', 'memberName', 'subjectText', updateFunction);
	}
});

Event.observe(self, 'load', updateFields);