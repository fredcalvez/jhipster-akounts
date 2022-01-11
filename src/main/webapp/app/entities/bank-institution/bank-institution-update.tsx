import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bank-institution.reducer';
import { IBankInstitution } from 'app/shared/model/bank-institution.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export const BankInstitutionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankInstitutionEntity = useAppSelector(state => state.bankInstitution.entity);
  const loading = useAppSelector(state => state.bankInstitution.loading);
  const updating = useAppSelector(state => state.bankInstitution.updating);
  const updateSuccess = useAppSelector(state => state.bankInstitution.updateSuccess);
  const currencyValues = Object.keys(Currency);
  const handleClose = () => {
    props.history.push('/bank-institution');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankInstitutionEntity,
      ...values,
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
          ...bankInstitutionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankInstitution.home.createOrEditLabel" data-cy="BankInstitutionCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankInstitution.home.createOrEditLabel">Create or edit a BankInstitution</Translate>
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
                  id="bank-institution-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankInstitution.institutionLabel')}
                id="bank-institution-institutionLabel"
                name="institutionLabel"
                data-cy="institutionLabel"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankInstitution.code')}
                id="bank-institution-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankInstitution.active')}
                id="bank-institution-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bankInstitution.currency')}
                id="bank-institution-currency"
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
                label={translate('akountsApp.bankInstitution.isoCountryCode')}
                id="bank-institution-isoCountryCode"
                name="isoCountryCode"
                data-cy="isoCountryCode"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-institution" replace color="info">
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

export default BankInstitutionUpdate;
