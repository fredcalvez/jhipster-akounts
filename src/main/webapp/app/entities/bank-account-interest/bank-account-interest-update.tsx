import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IBankAccount } from 'app/shared/model/bank-account.model';
import { getEntities as getBankAccounts } from 'app/entities/bank-account/bank-account.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-account-interest.reducer';
import { IBankAccountInterest } from 'app/shared/model/bank-account-interest.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankAccountInterestUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankAccounts = useAppSelector(state => state.bankAccount.entities);
  const bankAccountInterestEntity = useAppSelector(state => state.bankAccountInterest.entity);
  const loading = useAppSelector(state => state.bankAccountInterest.loading);
  const updating = useAppSelector(state => state.bankAccountInterest.updating);
  const updateSuccess = useAppSelector(state => state.bankAccountInterest.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-account-interest');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getBankAccounts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankAccountInterestEntity,
      ...values,
      creditedAccountId: bankAccounts.find(it => it.id.toString() === values.creditedAccountId.toString()),
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
          ...bankAccountInterestEntity,
          creditedAccountId: bankAccountInterestEntity?.creditedAccountId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankAccountInterest.home.createOrEditLabel" data-cy="BankAccountInterestCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankAccountInterest.home.createOrEditLabel">Create or edit a BankAccountInterest</Translate>
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
                  id="bank-account-interest-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.interest')}
                id="bank-account-interest-interest"
                name="interest"
                data-cy="interest"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.period')}
                id="bank-account-interest-period"
                name="period"
                data-cy="period"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.interestType')}
                id="bank-account-interest-interestType"
                name="interestType"
                data-cy="interestType"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.units')}
                id="bank-account-interest-units"
                name="units"
                data-cy="units"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.startDate')}
                id="bank-account-interest-startDate"
                name="startDate"
                data-cy="startDate"
                type="date"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.endDate')}
                id="bank-account-interest-endDate"
                name="endDate"
                data-cy="endDate"
                type="date"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.scrappingURL')}
                id="bank-account-interest-scrappingURL"
                name="scrappingURL"
                data-cy="scrappingURL"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.scrappingTag')}
                id="bank-account-interest-scrappingTag"
                name="scrappingTag"
                data-cy="scrappingTag"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankAccountInterest.scrappingTagBis')}
                id="bank-account-interest-scrappingTagBis"
                name="scrappingTagBis"
                data-cy="scrappingTagBis"
                type="text"
              />
              <ValidatedField
                id="bank-account-interest-creditedAccountId"
                name="creditedAccountId"
                data-cy="creditedAccountId"
                label={translate('akountsApp.bankAccountInterest.creditedAccountId')}
                type="select"
              >
                <option value="" key="0" />
                {bankAccounts
                  ? bankAccounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-account-interest" replace color="info">
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

export default BankAccountInterestUpdate;
