import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/akounts-settings">
      <Translate contentKey="global.menu.entities.akountsSettings" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/automatch">
      <Translate contentKey="global.menu.entities.automatch" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-category">
      <Translate contentKey="global.menu.entities.bankCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-account">
      <Translate contentKey="global.menu.entities.bankAccount" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-institution">
      <Translate contentKey="global.menu.entities.bankInstitution" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-account-interest">
      <Translate contentKey="global.menu.entities.bankAccountInterest" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-transaction">
      <Translate contentKey="global.menu.entities.bankTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/rebase-history">
      <Translate contentKey="global.menu.entities.rebaseHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-saving">
      <Translate contentKey="global.menu.entities.bankSaving" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-project">
      <Translate contentKey="global.menu.entities.bankProject" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-project-transaction">
      <Translate contentKey="global.menu.entities.bankProjectTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-stream">
      <Translate contentKey="global.menu.entities.bankStream" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-stream-transaction">
      <Translate contentKey="global.menu.entities.bankStreamTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-tag">
      <Translate contentKey="global.menu.entities.bankTag" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-tag-transaction">
      <Translate contentKey="global.menu.entities.bankTagTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-vendor">
      <Translate contentKey="global.menu.entities.bankVendor" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bank-transaction-automatch">
      <Translate contentKey="global.menu.entities.bankTransactionAutomatch" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bridge-account">
      <Translate contentKey="global.menu.entities.bridgeAccount" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bridge-run">
      <Translate contentKey="global.menu.entities.bridgeRun" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bridge-transaction">
      <Translate contentKey="global.menu.entities.bridgeTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bridge-user">
      <Translate contentKey="global.menu.entities.bridgeUser" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/budget">
      <Translate contentKey="global.menu.entities.budget" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/file-config">
      <Translate contentKey="global.menu.entities.fileConfig" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/file-import">
      <Translate contentKey="global.menu.entities.fileImport" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/plaid">
      <Translate contentKey="global.menu.entities.plaid" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
