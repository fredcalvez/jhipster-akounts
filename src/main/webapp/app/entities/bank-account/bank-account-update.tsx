import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankInstitution } from 'app/shared/model/bank-institution.model';
import { getEntities as getBankInstitutions } from 'app/entities/bank-institution/bank-institution.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-account.reducer';
import { IBankAccount } from 'app/shared/model/bank-account.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export const BankAccountUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankInstitutions = useAppSelector(state => state.bankInstitution.entities);
  const bankAccountEntity = useAppSelector(state => state.bankAccount.entity);
  const loading = useAppSelector(state => state.bankAccount.loading);
  const updating = useAppSelector(state => state.bankAccount.updating);
  const updateSuccess = useAppSelector(state => state.bankAccount.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const accountTypeValues = Object.keys(AccountType);
  const handleClose = () => {
    props.history.push('/bank-account');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankInstitutions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankAccountEntity,
      ...values,
      institution: bankInstitutions.find(it => it.id.toString() === values.institution.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          currency: 'AED',
          accountType: 'Check',
          ...bankAccountEntity,
          institution: bankAccountEntity?.institution?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankAccount.home.createOrEditLabel" data-cy="BankAccountCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankAccount.home.createOrEditLabel">Create or edit a BankAccount</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bank-account-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankAccount.accountLabel')}
                id="bank-account-accountLabel"
                name="accountLabel"
                data-cy="accountLabel"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccount.accountNumber')}
                id="bank-account-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccount.active')}
                id="bank-account-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccount.currency')}
                id="bank-account-currency"
                name="currency"
                data-cy="currency"
                type="select"
              >
                {currencyValues.map(currency => (
                  <option value={currency} key={currency}>
                    {translate('akountsApp.Currency.' + currency)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.bankAccount.initialAmount')}
                id="bank-account-initialAmount"
                name="initialAmount"
                data-cy="initialAmount"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccount.initialAmountLocal')}
                id="bank-account-initialAmountLocal"
                name="initialAmountLocal"
                data-cy="initialAmountLocal"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccount.accountType')}
                id="bank-account-accountType"
                name="accountType"
                data-cy="accountType"
                type="select"
              >
                {accountTypeValues.map(accountType => (
                  <option value={accountType} key={accountType}>
                    {translate('akountsApp.AccountType.' + accountType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('akountsApp.bankAccount.interest')}
                id="bank-account-interest"
                name="interest"
                data-cy="interest"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccount.nickname')}
                id="bank-account-nickname"
                name="nickname"
                data-cy="nickname"
                type="text"
              />
              <ValidatedField
                id="bank-account-institution"
                name="institution"
                data-cy="institution"
                label={translate('akountsApp.bankAccount.institution')}
                type="select"
              >
                <option value="" key="0" />
                {bankInstitutions
                  ? bankInstitutions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-account" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BankAccountUpdate;
